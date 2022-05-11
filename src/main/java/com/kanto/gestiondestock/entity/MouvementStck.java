package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class MouvementStck extends AbstractEntity {

    private Instant dateMouvement;

    private BigDecimal quantite;

    private TypeMvtStck typeMvtStck;

    private SourceMvtStock sourceMvtStock;

    @ManyToOne
    @JoinColumn(name = "idArticle")
    private Article article;

}
