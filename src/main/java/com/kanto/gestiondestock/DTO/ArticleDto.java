package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;


    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String codeArticle;


    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String designation;

    @NotNull(message="est obligatoire")
    @Min(value = 0, message = "doit etre superieur a 0")
    private BigDecimal prixUnitaireHT;

    @NotNull(message="est obligatoire")
    @Min(value = 0, message = "doit etre superieur a 0")
    private BigDecimal tauxTva;

    @NotNull(message="est obligatoire")
    @Min(value = 0, message = "doit etre superieur a 0")
    private BigDecimal prixUnitaireTTC;

    private String photo;

    @NotNull(message="est obligatoire")
    private CategorieDto categorie;

    @NotNull(message="est obligatoire")
    private EntrepriseDto entreprise;

    @JsonIgnore
    private List<MouvementStckDto> mouvementStcks;

    @JsonIgnore
    private List<LigneCommandeFournisseurDto> ligneCdeFournisseurs;

    @JsonIgnore
    private List<LigneCommandeClientDto> ligneCdeClients;

    @JsonIgnore
    private List<LigneVenteDto> ligneVentes;



    public static ArticleDto fromEntity(Article article) {

        if (article == null) {
            return null;
        }

        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireTTC(article.getPrixUnitaireTTC())
                .tauxTva(article.getTauxTva())
                .prixUnitaireHT(article.getPrixUnitaireHT())
                .photo(article.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(article.getEntreprise()))
                // article.getCategorie est de type CATEGORIE
                // alors que categorie est de type CategorieDto donc on va faire....
                .categorie(CategorieDto.fromEntity(article.getCategorie()))
                .build();
    }

    public static Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHT(articleDto.getPrixUnitaireHT());
        article.setPrixUnitaireTTC(articleDto.getPrixUnitaireTTC());
        article.setTauxTva(articleDto.getTauxTva());
        article.setPhoto(articleDto.getPhoto());
        article.setCategorie(CategorieDto.toEntity(articleDto.getCategorie()));
        article.setEntreprise(EntrepriseDto.toEntity(articleDto.getEntreprise()));

    return article;

    }
}
