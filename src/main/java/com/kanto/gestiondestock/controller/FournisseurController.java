package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.FournisseurDto;

import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.services.FournisseurService;
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
@Api("/fournisseurs")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @PostMapping(value = "/api/fournisseur/create")
    @ApiOperation(value = "Enregistrer un fournisseur (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier un fournisseur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet fournisseur creer/modifier")
    })
    public void save(@Valid @RequestBody FournisseurDto fournisseurDto) {

        if (fournisseurDto.getAdresse().getAdresse1() == null && fournisseurDto.getAdresse().getVille() == null) {
            log.error("Adresse est obligatoire");
            throw new EntityNotFoundException("Adresse est obligatoire");
        }

        fournisseurService.save(fournisseurDto);
    }

    @GetMapping(value = "/api/fournisseur/{id}")
    @ApiOperation(value = "Rechercher un fournisseur par ID", notes = "Cette methode permet de chercher une fournisseur par son ID", response = FournisseurDto.class)
    public FournisseurDto findById(@PathVariable Long id) {
        return fournisseurService.findById(id);
    }

    @GetMapping(value = "/api/fournisseurs")
    @ApiOperation(value = "Lister les fournisseurs", notes = "Cette methode permet de lister les fournisseurs", responseContainer = "List<FournisseurDto>")
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    @DeleteMapping(value = "/api/fournisseur/{id}")
    @ApiOperation(value = "Supprimer un fournisseur par son ID", notes = "Cette methode permet de supprimer un fournisseur par son ID")
    public void delete(@PathVariable Long id) {
        fournisseurService.delete(id);
    }
}
