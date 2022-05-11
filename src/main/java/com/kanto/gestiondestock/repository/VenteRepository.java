package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Vente, Long> {
}
