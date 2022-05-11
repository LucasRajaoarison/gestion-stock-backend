package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.CommandeClientDto;
import com.kanto.gestiondestock.DTO.CommandeFournisseurDto;
import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.DTO.LigneCommandeFournisseurDto;
import com.kanto.gestiondestock.entity.EtatCommande;
import com.kanto.gestiondestock.entity.LigneCdeFournisseur;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto);

    public CommandeFournisseurDto findById(Long id);

    public CommandeFournisseurDto findByCode(String code);

    public List<CommandeFournisseurDto> findAll();

    public void delete(Long id);

    public CommandeFournisseurDto updateEtatCommade(Long idCommande, EtatCommande etatCommande);

    public CommandeFournisseurDto updateFournisseur(Long idCommande, Long idFournisseur);

    public CommandeFournisseurDto updateArticle(Long idCommande, Long idLigneCommande, Long idArticle) ;

    public CommandeFournisseurDto  updateQuantiteCommande(Long idCommande, Long idLigneCommande, BigDecimal quantite) ;

    // Delete article ===> delete LigneCommandeFournisseur
    public CommandeFournisseurDto deleteArticle(Long idCommande, Long idLigneCommande);

    //chercher les lignes de commande fournisseur par commande
    public List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByCommandeFournisseurId(Long idCommande);

}
