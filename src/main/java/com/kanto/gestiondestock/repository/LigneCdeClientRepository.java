package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.LigneCdeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCdeClientRepository extends JpaRepository<LigneCdeClient, Long> {

    public List<LigneCdeClient> findAllByCommandeClientId(Long id);
    public List<LigneCdeClient> findAllByArticleId(Long id);
}
