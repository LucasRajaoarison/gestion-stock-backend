package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.CommandeClientDto;
import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.entity.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    public CommandeClientDto save(CommandeClientDto commandeClientDto);

    public CommandeClientDto updateEtatCommade(Long idCommande, EtatCommande etatCommande);

    public CommandeClientDto updateClient(Long idCommande, Long idClient);

    public CommandeClientDto updateArticle(Long idCommande, Long idLigneCommande, Long idArticle) ;

    public CommandeClientDto  updateQuantiteCommande(Long idCommande, Long idLigneCommande, BigDecimal quantite) ;

    // Delete article ===> delete LigneCommandeClient
    public CommandeClientDto deleteArticle(Long idCommande, Long idLigneCommande);

    //chercher les lignes de commande client par commande
    public List<LigneCommandeClientDto> findAllLigneCommandeClientByCommandeClientId(Long idCommande);

    public CommandeClientDto findById(Long id);

    public CommandeClientDto findByCode(String code);

    public List<CommandeClientDto> findAll();

    public void delete(Long id);


}
