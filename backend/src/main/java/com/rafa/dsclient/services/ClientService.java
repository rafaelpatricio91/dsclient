package com.rafa.dsclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafa.dsclient.entities.Client;
import com.rafa.dsclient.repositories.ClientRepository;
import com.rafa.dsclient.services.exceptions.DatabaseException;
import com.rafa.dsclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<Client> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = repository.findAll(pageRequest);
			
		return clients;
	}
	
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		
		Client client = obj.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found") );
		return client;
	}
	
	@Transactional
	public Client insert(Client c) {
		Client client = repository.save(c);
		
		return client;
	}
	
	@Transactional
	public Client update(Client c, Long id) {
		try {
//		Client client = repository.getOne(id);
			c.setId(id);
			c = repository.save(c);
			
			return c;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not Found: "+id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id Not Found: "+id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
