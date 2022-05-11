package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Long> {


    public Optional<CommandeFournisseur> findByCode(String code);

    public List<CommandeFournisseur> findAllByFournisseurId(Long id);
}
