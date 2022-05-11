package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    public Categorie findByCode(String code);

}
