package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeFournisseurDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String code;

    @NotNull(message="est obligatoire")
    private Instant dateCommande;

    @NotNull(message="est obligatoire")
    private EtatCommande etatCommande;

    @NotNull(message="est obligatoire")
    private FournisseurDto fournisseur;

    @NotNull(message="est obligatoire")
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseur;


    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur) {

        if (commandeFournisseur == null) {
            return null;
        }

        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .etatCommande(commandeFournisseur.getEtatCommande())
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .build() ;

    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto) {

        if (commandeFournisseurDto == null) {
            return null;
        }

        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
        commandeFournisseur.setFournisseur(FournisseurDto.toEntity(commandeFournisseurDto.getFournisseur()));

        return commandeFournisseur;

    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande) ;
    }

}
