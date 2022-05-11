package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Adresse;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String nom;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "est obligatoire") //= @NotBlank
    private String prenom;

    @NotNull(message="est obligatoire")
    private AdresseDto adresse;


    private String photo;

    @ValidEmail(message = "Email format incorrect")
    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String mail;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String numTel;

    @JsonIgnore
    private List<CommandeClientDto> commandeClients;


    public static ClientDto fromEntity(Client client) {

        if (client == null) {
            return null;
        }

        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .build() ;

    }

    public static Client toEntity(ClientDto clientDto) {

        if ( clientDto == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
        client.setMail(clientDto.getMail());
        client.setPhoto(clientDto.getPhoto());
        client.setNumTel(clientDto.getNumTel());

        return client;

    }


}
