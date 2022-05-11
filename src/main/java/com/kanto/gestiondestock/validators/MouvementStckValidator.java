package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.MouvementStckDto;
import com.kanto.gestiondestock.DTO.VenteDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MouvementStckValidator {

    public static List<String> validate(MouvementStckDto mouvementStckDto) {

        List<String> errors = new ArrayList<>();

        if (mouvementStckDto == null) {
            errors.add("Veuillez remplir les champs!");
            return errors;
        }
        if(mouvementStckDto.getTypeMvtStck() == null) {
            errors.add("Veuillez renseigner le champ 'Type de mouvement' ") ;
        }
        if(mouvementStckDto.getQuantite() == null) {
            errors.add("Veuillez renseigner le prix unitaire du mouvement") ;
        }
        if(mouvementStckDto.getDateMouvement() == null) {
            errors.add("Veuillez renseigner la date du mouvement") ;
        }
        if(mouvementStckDto.getArticle() == null) {
            errors.add("Veuillez renseigner le champ 'Article' du mouvement") ;
        }


        return errors;
    }

}
