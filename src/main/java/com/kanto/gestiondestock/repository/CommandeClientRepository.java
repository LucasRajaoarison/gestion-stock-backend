package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Long> {

    public Optional<CommandeClient> findByCode(String code);

    public List<CommandeClient> findAllByClientId(Long id);
}
