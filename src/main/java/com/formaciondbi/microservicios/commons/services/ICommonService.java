package com.formaciondbi.microservicios.commons.services;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * La interface recibe una clase generica "E", en lugar del entity.
 * @author balti
 *
 * @param <E>
 */
public interface ICommonService <E>{

	public Iterable<E> findAll();
	
	public Page<E> findAll(final Pageable pageable);
	
	public Optional<E> findById(Long lId);
	
	public E guardar(E entity);
	
	public void deleteById(Long lId);
}
