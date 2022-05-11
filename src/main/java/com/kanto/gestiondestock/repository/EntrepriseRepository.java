package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Entreprise;
import com.kanto.gestiondestock.entity.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
	
	
	public Entreprise findByMail(String mail);
}
