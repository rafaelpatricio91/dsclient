package com.rafa.dsclient.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rafa.dsclient.entities.Client;
import com.rafa.dsclient.services.ClientService;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {

	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<Page<Client>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "8") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<Client> clients = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(clients);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = new Client();
		client = service.findById(id);
		
		return ResponseEntity.ok().body(client);
	}
	
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client entity) {
		Client c = service.insert(entity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
		
		return ResponseEntity.created(uri).body(c);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
		client = service.update(client, id);
		
		return ResponseEntity.ok().body(client);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Client> delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
