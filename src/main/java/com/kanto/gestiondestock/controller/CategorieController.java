package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.entity.Categorie;
import com.kanto.gestiondestock.services.CategorieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Api("/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @PostMapping(value = "/api/categorie/create")
    @ApiOperation(value = "Enregistrer une categorie (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier une categorie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie creer/modifier")
    })
    public CategorieDto save(@Valid @RequestBody CategorieDto categorieDto) {

        CategorieDto savedCategory = categorieService.save(categorieDto);

       return savedCategory;
    }

    @GetMapping(value = "/api/categorie/{id}")
    @ApiOperation(value = "Rechercher une categorie par ID", notes = "Cette methode permet de chercher une categorie par son ID", response = CategorieDto.class)
    public CategorieDto findById(@PathVariable Long id) {
       return categorieService.findById(id);
    }

    @GetMapping(value = "/api/categorie/code/{code}")
    @ApiOperation(value = "Rechercher une categorie par Code", notes = "Cette methode permet de chercher une categorie par son code", response = CategorieDto.class)
    public CategorieDto findByCodeCategorie(@PathVariable("code") String codeCategorie) {
        return categorieService.findByCodeCategorie(codeCategorie);
    }

    @GetMapping(value = "/api/categories")
    @ApiOperation(value = "Lister les categories", notes = "Cette methode permet de lister les categories", responseContainer = "List<CategorieDto>")
    public List<CategorieDto> findAll() {
        return categorieService.findAll();
    }

    @DeleteMapping(value = "/api/categorie/{id}")
    @ApiOperation(value = "Supprimer une categorie par son ID", notes = "Cette methode permet de supprimer une categorie par son ID")
    public void delete(@PathVariable Long id) {
            categorieService.delete(id);
    }
}
