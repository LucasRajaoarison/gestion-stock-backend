package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.LigneVente;
import com.kanto.gestiondestock.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

    List<LigneVente> findAllByArticleId(Long id);

    List<LigneVente> findAllByVenteId(Long id);
}
