package com.rafa.dsclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafa.dsclient.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {}
