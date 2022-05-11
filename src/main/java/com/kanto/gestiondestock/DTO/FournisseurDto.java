package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Adresse;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.entity.CommandeFournisseur;
import com.kanto.gestiondestock.entity.Fournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FournisseurDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String nom;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String prenom;


    @Size(min=1, message="est obligatoire")
    private AdresseDto adresse;

    private String photo;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String mail;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String numTel;

    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;


    public static FournisseurDto fromEntity(Fournisseur fournisseur) {

        if (fournisseur == null) {
            return null;
        }

        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .photo(fournisseur.getPhoto())
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getNumTel())
                .build() ;

    }

    public static Fournisseur toEntity(FournisseurDto fournisseurDto) {

        if ( fournisseurDto == null) {
            return null;
        }

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setAdresse(AdresseDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setNumTel(fournisseurDto.getNumTel());

        return fournisseur;

    }

}
