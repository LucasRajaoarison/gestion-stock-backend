package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.EntrepriseDto;
import com.kanto.gestiondestock.DTO.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Entreprisevalidators {

    public static List<String> validate(EntrepriseDto entrepriseDto) {

        List<String> errors = new ArrayList<>();

        if (entrepriseDto == null) {
            errors.add("Veuillez remplir les champs!");
            return errors;
        }

        if(!StringUtils.hasLength(entrepriseDto.getNom())) {
            errors.add("Veuillez renseigner le nom de l'entreprise") ;
        }
        if(!StringUtils.hasLength(entrepriseDto.getCodeFiscal())) {
            errors.add("Veuillez renseigner le prenom du fournisseur") ;
        }
        if(!StringUtils.hasLength(entrepriseDto.getMail())) {
            errors.add("Veuillez renseigner le nom du fournisseur") ;
        }
        if(!StringUtils.hasLength(entrepriseDto.getNumTel())) {
            errors.add("Veuillez renseigner le nom du fournisseur") ;
        }

        if(entrepriseDto.getAdresse() == null) {
            errors.add("Veuillez renseigner l'adresse de l'entreprise") ;

        } else {

            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getAdresse1())) {
                errors.add("Le champ 'Adresse1' est obligatoire ") ;
            }
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getVille())) {
                errors.add("Le champ 'Ville' est obligatoire ") ;
            }
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getCodePostal())) {
                errors.add("Le champ 'Code Postal' est obligatoire") ;
            }
            if(!StringUtils.hasLength(entrepriseDto.getAdresse().getPays())) {
                errors.add("Le champ 'Pays' est obligatoire") ;
            }

        }


        return errors;
    }

}
