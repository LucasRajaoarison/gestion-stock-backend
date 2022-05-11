package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.CommandeClientDto;
import com.kanto.gestiondestock.DTO.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto commandeClientDto) {

        List<String> errors = new ArrayList<>();

        if (commandeClientDto == null) {
            errors.add("Veuillez remplir les champs!");
            return errors;
        }

        if(!StringUtils.hasLength(commandeClientDto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande") ;
        }
        if(commandeClientDto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande") ;
        }
        if(commandeClientDto.getClient() == null) {
            errors.add("Veuillez renseigner le champ 'Client' ") ;
        }

        return errors;
    }

}
