package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.EntrepriseDto;

import com.kanto.gestiondestock.entity.Entreprise;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.services.EntrepriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api("/entreprises")
public class EntrepriseController{

    @Autowired
    private EntrepriseService entrepriseService;

    @PostMapping(value = "/api/entreprise/create")
    @ApiOperation(value = "Enregistrer un entreprise (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier un entreprise")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet entreprise creer/modifier")
    })
    public EntrepriseDto save(@Valid  @RequestBody EntrepriseDto entrepriseDto, BindingResult bindingResult) {

    	/* if(bindingResult.hasErrors()){
            System.err.println("error!");
            Map<String, String> errors = new HashMap<>();
            for (FieldError error:bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
        } */
    	
       if (entrepriseDto.getAdresse().getAdresse1() == null && entrepriseDto.getAdresse().getVille() == null) {
           log.error("Adresse est obligatoire");
           throw new EntityNotFoundException("Adresse est obligatoire");
      }

       EntrepriseDto savedEntreprise = entrepriseService.save(entrepriseDto);
        
        return savedEntreprise;
    }

    @GetMapping(value = "/api/entreprise/{id}")
    @ApiOperation(value = "Rechercher une entreprise par ID", notes = "Cette methode permet de chercher une entreprise par son ID", response = EntrepriseDto.class)
    public EntrepriseDto findById(@PathVariable Long id) {
        return entrepriseService.findById(id);
    }

    @GetMapping(value = "/api/entreprises")
    @ApiOperation(value = "Lister les entreprises", notes = "Cette methode permet de lister les entreprises", responseContainer = "List<EntrepriseDto>")
    public List<EntrepriseDto> findAll() {
        return entrepriseService.findAll();
    }

    @DeleteMapping(value = "/api/entreprise/{id}")
    @ApiOperation(value = "Supprimer un entreprise par son ID", notes = "Cette methode permet de supprimer un entreprise par son ID")
    public void delete(@PathVariable Long id) {
        entrepriseService.delete(id);
    }
}
