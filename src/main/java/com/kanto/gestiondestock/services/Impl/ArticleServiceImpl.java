package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.*;
import com.kanto.gestiondestock.entity.*;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidEntityException;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.*;
import com.kanto.gestiondestock.services.ArticleService;
import com.kanto.gestiondestock.services.CategorieService;
import com.kanto.gestiondestock.validators.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    private LigneCdeFournisseurRepository ligneCdeFournisseurRepository;

    @Autowired
    private LigneCdeClientRepository ligneCdeClientRepository;


    @Override
    public ArticleDto save(ArticleDto articleDto) {

        if (articleDto.getCategorie().getId() != null) {
            Categorie categorie = categorieRepository.findById(articleDto.getCategorie().getId()).orElse(null) ;
            articleDto.setCategorie(CategorieDto.fromEntity(categorie));
        }

        if (articleDto.getEntreprise().getId() != null) {
            Entreprise entreprise = entrepriseRepository.findById(articleDto.getEntreprise().getId()).orElse(null);
            articleDto.setEntreprise(EntrepriseDto.fromEntity(entreprise));
        }

        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(articleDto)
                ));
    }

    @Override
    public ArticleDto findById(Long id) {

        if (id == null) {
            log.error("Article ID est null");
            return null;
        }

        Optional<Article> article = Optional.of(articleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec l'ID : " + id + " n'a ete trouver dans la BDD")
        ));

        return (ArticleDto.fromEntity(article.get()));
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {

        if (!StringUtils.hasLength(codeArticle)){
            log.error("Code article est null");
            return null;
        }

        Article articleByCode = articleRepository.findByCodeArticle(codeArticle);

        if ( articleByCode != null) {
            return ArticleDto.fromEntity(articleByCode);

        } else {
            throw new EntityNotFoundException("Aucun article avec ce code est trouver " + codeArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)    //mapper un objet a un autre,
                .collect(Collectors.toList());  // spring detecte autom le param et donc pas besoin de param dans fromEntity

    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Article ID est null pour la delete");
            return;
        }

        List<LigneCdeClient> ligneCdeClients = ligneCdeClientRepository.findAllByArticleId(id);
        if (!ligneCdeClients.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer un article deja utilise dans des Commandes clients",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneCdeFournisseur> ligneCdeFournisseurs  = ligneCdeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCdeFournisseurs.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer un article deja utilise dans des Commandes fournisseurs",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneVente> ligneVentes  = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        articleRepository.deleteById(id);
    }


    @Override
    public List<LigneVenteDto> findHistoriqueVente(Long idArticle) {
        return ligneVenteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Long idArticle) {
        return ligneCdeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Long idArticle) {
        return ligneCdeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByCategoryId(Long idCategory) {
        return articleRepository.findAllByCategorieId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }
}
