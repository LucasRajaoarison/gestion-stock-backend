package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.DTO.LigneCommandeFournisseurDto;
import com.kanto.gestiondestock.DTO.LigneVenteDto;
import com.kanto.gestiondestock.entity.Article;

import java.util.List;

public interface ArticleService {

    public ArticleDto save(ArticleDto articleDto);

    public ArticleDto findById(Long Id);

    public ArticleDto findByCodeArticle(String codeArticle);

    public List<ArticleDto> findAll();

    public void delete(Long id);

    public List<LigneVenteDto> findHistoriqueVente(Long idArticle);

    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Long idArticle);

    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Long idArticle);

    public List<ArticleDto> findAllArticleByCategoryId(Long idCategory);

}
