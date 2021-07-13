package com.formaciondbi.microservicios.commons.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;



/**
 * R hereda de CrudRepository
 * Se crea un servicio generico.
 * @param lId
 */
public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements ICommonService<E>{

	@Autowired
	protected R repo;
	@Override
	@Transactional(readOnly = true)
	public Iterable<E> findAll() {		
		return this.repo.findAll(Sort.by("id"));
	}
	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(final Pageable pageable){
		return this.repo.findAll(pageable);
	}
	@Transactional(readOnly = true)
	@Override
	public Optional<E> findById(Long lId) {
		return this.repo.findById(lId);
	}

	@Override
	@Transactional
	public E guardar(E entity) {		
		return this.repo.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long lId) {
		this.repo.deleteById(lId);
	}

}
