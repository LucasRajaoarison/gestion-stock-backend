package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Adresse  {

    public String adresse1;

    public String adresse2;

    public String ville;

    public String codePostal;

    public String pays;


}
