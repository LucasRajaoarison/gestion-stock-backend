package com.kanto.gestiondestock.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.entity.LigneCdeClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneCommandeClientDto {

    private Long id;

    @NotNull(message="est obligatoire")
    private BigDecimal quantite;

    @NotNull(message="est obligatoire")
    private BigDecimal prixUnitaire;

    @NotNull(message="est obligatoire")
    @JsonIgnore
    private ArticleDto article;

    @JsonIgnore
    private CommandeClientDto commandeClient;



    public static LigneCommandeClientDto fromEntity(LigneCdeClient ligneCdeClient) {

        if (ligneCdeClient == null) {
            return null;
        }

        return LigneCommandeClientDto.builder()
                .id(ligneCdeClient.getId())
                .quantite(ligneCdeClient.getQuantite())
                .prixUnitaire(ligneCdeClient.getPrixUnitaire())
                .article(ArticleDto.fromEntity(ligneCdeClient.getArticle()))
                .commandeClient(CommandeClientDto.fromEntity(ligneCdeClient.getCommandeClient()))
                .build() ;


    }

    public static LigneCdeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto) {

        if (ligneCommandeClientDto == null) {
            return null;
        }

        LigneCdeClient ligneCdeClient = new LigneCdeClient();
        ligneCdeClient.setId(ligneCommandeClientDto.getId());
        ligneCdeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCdeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCdeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCdeClient.setCommandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeClient()));

        return ligneCdeClient;

    }

}
