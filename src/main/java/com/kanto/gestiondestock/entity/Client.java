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
public class Client extends AbstractEntity {

    @Column(length = 76)
    private String nom;

    @Column(length = 76)
    private String prenom;

    @Embedded //champs composé que l'on peut utiliser dans tous nos entités
    private Adresse adresse;

    @Column(length = 76)
    private String photo;

    @Column(length = 76)
    private String mail;

    @Column(length = 76)
    private String numTel;

    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients = new ArrayList<>();






}
