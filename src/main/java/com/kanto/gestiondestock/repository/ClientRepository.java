package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
