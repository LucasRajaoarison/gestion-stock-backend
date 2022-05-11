package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.LigneCdeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCdeFournisseurRepository extends JpaRepository<LigneCdeFournisseur, Long> {
    public List<LigneCdeFournisseur> findAllByCommandeFournisseurId(Long id) ;
    public List<LigneCdeFournisseur> findAllByArticleId(Long id) ;
}
