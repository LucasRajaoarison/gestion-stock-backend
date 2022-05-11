package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    public Utilisateur findByMail(String mail);

}

