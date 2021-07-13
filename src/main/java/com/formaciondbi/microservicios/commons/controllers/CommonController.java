package com.formaciondbi.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formaciondbi.microservicios.commons.services.ICommonService;

/*
 * El api gateway enruta de forma dinamica el acceso a cada microservicio, como unico punto de entrada
 * , a travez de filtros y usa balanceo de carga en automatico, seguriza las rutas con spring security.
 * cada 30 segundos los microservicios se comunican con EUREKA para indicar que esta activo, 
 * si se baja una instancia en 90 segudos EUREKA lo elimina, en 90 segundos EUREKA lo registra cuando se levanta
 * el microservicio.
 * 
 * "E" representa la entity
 * "S" extiende de la interface de la del servicio que recibe un entity "E".
 */
//@CrossOrigin({"http://localhost:4200"})//Permite acceso a esta ruta, o aplicativo
public class CommonController<E, S extends ICommonService<E> > {

	@Autowired
	protected S service;
	
	@GetMapping//se mapea a la raiz.
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(this.service.findAll());
	}
	@GetMapping("/paginable")//se mapea a la raiz.
	public ResponseEntity<?> listarPaginado(final Pageable pageable){
		return ResponseEntity.ok().body(this.service.findAll(pageable));
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> verdetalle(@PathVariable("id")final Long lId){
		Optional<E> tmp = this.service.findById(lId);
		if(tmp.isPresent()) {
			return ResponseEntity.ok().body(tmp.get());
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody E entity , final BindingResult result){
		if(result.hasErrors()) {
			return validar(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(entity));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") final Long lId){
		this.service.deleteById(lId);
		return ResponseEntity.noContent().build();//no tiene cuerpo de respuesta
	}
	/**
	 * Método que valida los campos de los entityes.
	 * @param result
	 * @return
	 */
	protected ResponseEntity<?>validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(error ->{ 
			errores.put(error.getField(), "El campo "+ error.getField() + " "+ error.getDefaultMessage());
			});
		return ResponseEntity.badRequest().body(errores);	
	}
}
