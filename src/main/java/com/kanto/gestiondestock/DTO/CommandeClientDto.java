package com.kanto.gestiondestock.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.entity.EtatCommande;
import com.kanto.gestiondestock.entity.LigneCdeClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeClientDto {

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
    private ClientDto client;

    @NotNull(message="est obligatoire")
    private List<LigneCommandeClientDto> ligneCdeClients;


    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {

        if (commandeClient == null) {
            return null;
        }

        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .build() ;

    }


    public static CommandeClient toEntity(CommandeClientDto commandeClientDto) {

        if (commandeClientDto == null) {
            return null;
        }

        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));

        return commandeClient;

    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande) ;
    }


}
