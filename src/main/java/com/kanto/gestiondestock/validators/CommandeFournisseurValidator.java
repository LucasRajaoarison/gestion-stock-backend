package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.CommandeClientDto;
import com.kanto.gestiondestock.DTO.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDto commandeFournisseurDto) {

        List<String> errors = new ArrayList<>();

        if (commandeFournisseurDto == null) {
            errors.add("Veuillez remplir les champs!");
            return errors;
        }

        if(!StringUtils.hasLength(commandeFournisseurDto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande") ;
        }
        if(commandeFournisseurDto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande") ;
        }
        if(commandeFournisseurDto.getFournisseur() == null) {
            errors.add("Veuillez renseigner le champ 'Client' ") ;
        }

        return errors;
    }

}
