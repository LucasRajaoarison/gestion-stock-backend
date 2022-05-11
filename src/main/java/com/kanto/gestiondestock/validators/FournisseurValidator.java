package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.DTO.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto fournisseurDto) {

        List<String> errors = new ArrayList<>();

        if (fournisseurDto == null) {
            errors.add("Veuillez remplir les champs!");
            return errors;
        }

        if(!StringUtils.hasLength(fournisseurDto.getNom())) {
            errors.add("Veuillez renseigner le nom du fournisseur") ;
        }
        if(!StringUtils.hasLength(fournisseurDto.getPrenom())) {
            errors.add("Veuillez renseigner le prenom du fournisseur") ;
        }
        if(!StringUtils.hasLength(fournisseurDto.getMail())) {
            errors.add("Veuillez renseigner le nom du fournisseur") ;
        }
        if(!StringUtils.hasLength(fournisseurDto.getNumTel())) {
            errors.add("Veuillez renseigner le nom du fournisseur") ;
        }
        return errors;
    }

}
