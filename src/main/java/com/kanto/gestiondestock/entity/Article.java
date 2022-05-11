package com.kanto.gestiondestock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Article extends AbstractEntity {

    @Column(length = 76)
    @NotEmpty(message = "est obligatoire")
    private String codeArticle;

    @Column(length = 76)
    @NotEmpty(message = "est obligatoire")
    private String designation;

    private BigDecimal prixUnitaireHT;

    private BigDecimal tauxTva;

    private BigDecimal prixUnitaireTTC;

    @Column(length = 126)
    private String photo;

    @Transient
    private Long idCategorieTrans;

    @Transient
    private Long idEntrepriseTrans;

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "idEntreprise")
    private Entreprise entreprise;


    @OneToMany(mappedBy = "article")
    private List<MouvementStck> mouvementStcks = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<LigneCdeFournisseur> ligneCdeFournisseurs = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<LigneCdeClient> ligneCdeClients = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes = new ArrayList<>();

}
