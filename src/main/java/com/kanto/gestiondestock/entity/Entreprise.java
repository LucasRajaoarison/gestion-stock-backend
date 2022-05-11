package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Entreprise extends AbstractEntity {

    @Column(length = 76)
    private String nom;

    @Column(length = 76)
    private String description;

    @Embedded //champs composé que l'on peut utiliser dans tous nos entités
    private Adresse adresse;

    //private String password;

    @Column(length = 76)
    private String codeFiscal;

    @Column(length = 76)
    private String mail;

    @Column(length = 76)
    private String photo;

    @Column(length = 76)
    private String numTel;
    

    @OneToMany(mappedBy = "entreprise")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

}
