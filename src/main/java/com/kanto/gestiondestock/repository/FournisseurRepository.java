package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}
