package com.kanto.gestiondestock.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.CommandeFournisseur;
import com.kanto.gestiondestock.entity.LigneCdeClient;
import com.kanto.gestiondestock.entity.LigneCdeFournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneCommandeFournisseurDto {

    private Long id;

    @NotNull(message="est obligatoire")
    private BigDecimal quantite;

    @NotNull(message="est obligatoire")
    private BigDecimal prixUnitaire;

    @NotNull(message="est obligatoire")
    @JsonIgnore
    private ArticleDto article;

    @JsonIgnore
    private CommandeFournisseurDto commandeFournisseur;


    public static LigneCommandeFournisseurDto fromEntity(LigneCdeFournisseur ligneCdeFournisseur) {

        if (ligneCdeFournisseur == null) {
            return null;
        }

        return LigneCommandeFournisseurDto.builder()
                .id(ligneCdeFournisseur.getId())
                .quantite(ligneCdeFournisseur.getQuantite())
                .prixUnitaire(ligneCdeFournisseur.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneCdeFournisseur.getArticle()))
                .commandeFournisseur(CommandeFournisseurDto.fromEntity(ligneCdeFournisseur.getCommandeFournisseur()))
                .build() ;


    }

    public static LigneCdeFournisseur toEntity(LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {

        if (ligneCommandeFournisseurDto == null) {
            return null;
        }

        LigneCdeFournisseur ligneCdeFournisseur = new LigneCdeFournisseur();
        ligneCdeFournisseur.setId(ligneCommandeFournisseurDto.getId());
        ligneCdeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
        ligneCdeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
        ligneCdeFournisseur.setArticle(ArticleDto.toEntity(ligneCommandeFournisseurDto.getArticle()));
        ligneCdeFournisseur.setCommandeFournisseur(CommandeFournisseurDto.toEntity(ligneCommandeFournisseurDto.getCommandeFournisseur()));

        return ligneCdeFournisseur;

    }

    /*public static LigneCdeFournisseur toEntity(LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {

        if (ligneCommandeFournisseurDto == null) {
            return null;
        }

        LigneCdeFournisseur ligneCdeFournisseur = new LigneCdeFournisseur();
        ligneCdeFournisseur.setId(ligneCommandeFournisseurDto.getId());
        ligneCdeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
        ligneCdeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
        ligneCdeFournisseur.setArticle(ArticleDto.toEntity(ligneCommandeFournisseurDto.getArticle()));
        ligneCdeFournisseur.setCommandeFournisseur(CommandeFournisseurDto.toEntity(ligneCommandeFournisseurDto.getCommandeFournisseur()));

        return ligneCdeFournisseur;

    }*/


}
