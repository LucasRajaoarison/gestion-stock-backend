package com.kanto.gestiondestock.validators;

import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto articleDto) {

        List<String> errors = new ArrayList<>();

        if (articleDto == null) {
            errors.add("Veuillez remplir les champs!");
            return errors;
        }
        if(!StringUtils.hasLength(articleDto.getCodeArticle())) {
            errors.add("Veuillez renseigner le code de l'article") ;
        }
        if(!StringUtils.hasLength(articleDto.getDesignation())) {
            errors.add("Veuillez renseigner la designation de l'article") ;
        }
        if(articleDto.getPrixUnitaireHT() == null) {
            errors.add("Veuillez renseigner le champs 'Prix Unitaire HT' ") ;
        }
        if(articleDto.getTauxTva() == null) {
            errors.add("Veuillez renseigner le champs 'Taux TVA' ") ;
        }
        if(articleDto.getPrixUnitaireTTC() == null) {
            errors.add("Veuillez renseigner le champs 'Prix Unitaire TTC' ") ;
        }
        if(articleDto.getCategorie() == null) {
            errors.add("Veuillez renseigner le champs 'Categorie' ") ;
        }

        return errors;

    }
}
