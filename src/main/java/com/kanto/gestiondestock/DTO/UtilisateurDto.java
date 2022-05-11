package com.kanto.gestiondestock.DTO;


import com.kanto.gestiondestock.entity.Adresse;
import com.kanto.gestiondestock.entity.Entreprise;
import com.kanto.gestiondestock.entity.Role;
import com.kanto.gestiondestock.entity.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String nom;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String prenom;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String mail;

    @NotNull(message="est obligatoire")
    private Instant dateDeNaissance;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String password;

    @NotNull(message="est obligatoire")
    private AdresseDto adresse;

    private String photo;

    @NotNull(message="est obligatoire")
    private EntrepriseDto entreprise;

    @NotNull(message="est obligatoire")
    private Collection<RoleDto> roles ;

    public UtilisateurDto(Long id, String mail, String password) {
        this.id = id;
        this.mail = mail;
        this.password = password;
    }


    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {

        if (utilisateur == null){
            return null;
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .mail(utilisateur.getMail())
                .password(utilisateur.getPassword())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                .map(RoleDto::fromEntity)
                                .collect(Collectors.toList()) : null
                )
                .build();

    }


    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {

        if (utilisateurDto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setMail(utilisateurDto.getMail());
        utilisateur.setPassword(utilisateurDto.getPassword());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        utilisateur.setRoles(
                utilisateurDto.getRoles() != null ?
                        utilisateurDto.getRoles().stream()
                        .map(RoleDto::toEntity)
                        .collect(Collectors.toList()) : null
        );

        return utilisateur;
    }


}
