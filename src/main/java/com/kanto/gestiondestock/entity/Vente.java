package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Vente extends AbstractEntity {

    @Column(length = 76)
    private String code;

    private Instant dateVente;

    private String commentaire;

    @OneToMany(mappedBy = "vente")
    private List<LigneVente> ligneVentes = new ArrayList<>();
}
