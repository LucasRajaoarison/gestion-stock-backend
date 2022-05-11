package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    private String nom;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String description;

    @NotNull(message="est obligatoire")
    private AdresseDto adresse;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String codeFiscal;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String mail;

    private String photo;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String numTel;
 
    
    @JsonIgnore
    private List<AdresseDto> articles;

    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;


    public static EntrepriseDto fromEntity(Entreprise entreprise) {

        if (entreprise == null) {
            return null;
        }

        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .photo(entreprise.getPhoto())
                .mail(entreprise.getMail())
                .numTel(entreprise.getNumTel())
                .codeFiscal(entreprise.getCodeFiscal())
                .description(entreprise.getDescription())
                .build() ;

    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto) {

        if ( entrepriseDto == null) {
            return null;
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
        entreprise.setMail(entrepriseDto.getMail());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());

        return entreprise;

    }

}
