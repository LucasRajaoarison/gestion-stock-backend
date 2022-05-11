package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class CommandeFournisseur extends AbstractEntity {

    @Column(length = 76)
    private String code;

    @DateTimeFormat(pattern= "dd/MM/yyyy")
    private Instant dateCommande;

    @Column(length = 76)
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "idFournisseur")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCdeFournisseur> ligneCdeFournisseurs = new ArrayList<>();


}
