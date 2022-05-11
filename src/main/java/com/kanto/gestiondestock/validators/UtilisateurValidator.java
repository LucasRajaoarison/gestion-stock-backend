package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto) {

        List<String> errors = new ArrayList<>();

        if (utilisateurDto == null) {
            errors.add("Veuillez remplir les champs!") ;
            return errors;
        }

            if(!StringUtils.hasLength(utilisateurDto.getNom())) {
                errors.add("Veuillez renseigner le nom de l'utilsateur") ;
            }
            if(!StringUtils.hasLength(utilisateurDto.getPrenom())) {
                errors.add("Veuillez renseigner le prenom de l'utilsateur") ;
            }
            if(!StringUtils.hasLength(utilisateurDto.getMail())) {
                errors.add("Veuillez renseigner le Mail de l'utilsateur") ;
            }
            if(!StringUtils.hasLength(utilisateurDto.getPassword())) {
                errors.add("Veuillez renseigner le mot de passe de l'utilsateur") ;
            }
            if (utilisateurDto.getDateDeNaissance() == null) {
                errors.add("Veuillez renseigner la date de naissance de l'utilsateur") ;
            }
            if(utilisateurDto.getAdresse() == null) {
                errors.add("Veuillez renseigner l'adresse de l'utilsateur") ;

            } else {

                if(!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())) {
                    errors.add("Le champ 'Adresse1' est obligatoire ") ;
                }
                if(!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())) {
                    errors.add("Le champ 'Ville' est obligatoire ") ;
                }
                if(!StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostal())) {
                    errors.add("Le champ 'Code Postal' est obligatoire") ;
                }
                if(!StringUtils.hasLength(utilisateurDto.getAdresse().getPays())) {
                    errors.add("Le champ 'Pays' est obligatoire") ;
                }

            }






        return errors;

    }

}
