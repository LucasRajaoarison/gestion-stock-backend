package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Utilisateur extends AbstractEntity {

    @Column(length = 76)
    private String nom;

    @Column(length = 76)
    private String prenom;

    @Column(length = 76)
    private String mail;

    @Column(length = 76)
    private Instant dateDeNaissance;

    @Column(length = 76)
    private String password;

    @Embedded //champs composé que l'on peut utiliser dans tous nos entités
    private Adresse adresse;

    @Column(length = 76)
    private String photo;

    @ManyToOne
    @JoinColumn(name = "idEntreprise")
    private Entreprise entreprise;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>() ;

}
