package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class LigneCdeClient extends AbstractEntity {

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    @Transient
    private Long idArticleTrans;

    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;


    @ManyToOne
    @JoinColumn(name = "idCommandeClient")
    private CommandeClient commandeClient;

}
