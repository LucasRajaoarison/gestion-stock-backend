package com.kanto.gestiondestock.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.LigneCdeFournisseur;
import com.kanto.gestiondestock.entity.LigneVente;
import com.kanto.gestiondestock.entity.Vente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneVenteDto {

    private Long id;

    @NotNull(message="est obligatoire")
    private BigDecimal quantite;

    @NotNull(message="est obligatoire")
    private BigDecimal prixUnitaire;

    @NotNull(message="est obligatoire")
    private ArticleDto article;

    @JsonIgnore
    private VenteDto vente;




    public static LigneVenteDto fromEntity(LigneVente ligneVente) {

        if (ligneVente == null) {
            return null;
        }

        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                .vente(VenteDto.fromEntity(ligneVente.getVente()))
                .build() ;


    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto) {

        if (ligneVenteDto == null) {
            return null;
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setArticle(ArticleDto.toEntity(ligneVenteDto.getArticle()));
        ligneVente.setVente(VenteDto.toEntity(ligneVenteDto.getVente()));

        return ligneVente;

    }

}
