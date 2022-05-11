package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class CommandeClient extends AbstractEntity {

    @Column(length = 76)
    private String code;

    @DateTimeFormat(pattern= "dd/MM/yyyy")
    private Instant dateCommande;

    @Column(length = 76)
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @Transient
    private Long idClientTrans;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commandeClient")
    private List<LigneCdeClient> ligneCdeClients = new ArrayList<>();


    /*public void add(LigneCdeClient ligne) {

        if (ligne != null) {
            if (ligneCdeClients == null) {
                ligneCdeClients = new HashSet<>();
            }
            ligneCdeClients.add(ligne);
            ligne.setCommandeClient(this);
        }

    }*/


}
