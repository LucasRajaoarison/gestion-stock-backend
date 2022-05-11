package com.kanto.gestiondestock.DTO;

import com.kanto.gestiondestock.entity.Adresse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresseDto {

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    public String adresse1;

    public String adresse2;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    public String ville;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    public String codePostal;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    public String pays;

    //
    //Avec l'utilisation de @Builder comme design pattern
    //
    public static AdresseDto fromEntity(Adresse adresse) {

        if (adresse == null) {
            return null;
        }

         return AdresseDto.builder()
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .ville(adresse.getVille())
                .codePostal(adresse.getCodePostal())
                .pays(adresse.getPays())
                .build() ;
    }

    public static Adresse toEntity(AdresseDto adresseDto) {
        if (adresseDto == null) {
            return null;
        }

        Adresse adresse = new Adresse();
        adresse.setPays(adresseDto.getPays());
        adresse.setAdresse1(adresseDto.getAdresse1());
        adresse.setAdresse2(adresseDto.getAdresse2());
        adresse.setVille(adresseDto.getVille());
        adresse.setCodePostal(adresseDto.getCodePostal());

        return adresse;

    }


}
