package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.ChangerPasswordUtilisateurDto;
import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.services.UtilisateurService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Api("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;


    @PostMapping(value = "/api/utilisateur/create")
    @ApiOperation(value = "Enregistrer un utilisateur (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet utilisateur creer/modifier")
    })
    public ResponseEntity<UtilisateurDto> save(@Valid @RequestBody  UtilisateurDto utilisateurDto) {

        if (utilisateurDto.getAdresse().getAdresse1() == null && utilisateurDto.getAdresse().getVille() == null) {
            log.error("Adresse est obligatoire");
            throw new EntityNotFoundException("Adresse est obligatoire");
        }

        return ResponseEntity.ok(utilisateurService.save(utilisateurDto)) ;

    }

    @GetMapping(value = "/api/utilisateur/{id}")
    @ApiOperation(value = "Rechercher une utilisateur par ID", notes = "Cette methode permet de chercher un utilisateur par son ID", response = UtilisateurDto.class)
    public ResponseEntity<UtilisateurDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(utilisateurService.findById(id)) ;

    }

    @GetMapping(value = "/api/utilisateurs")
    @ApiOperation(value = "Lister les utilisateurs", notes = "Cette methode permet de lister les utilisateurs", responseContainer = "List<UtilisateurDto>")
    public ResponseEntity<List<UtilisateurDto>> findAll() {

        return ResponseEntity.ok(utilisateurService.findAll());

    }

    @DeleteMapping(value = "/api/utilisateur/{id}")
    @ApiOperation(value = "Supprimer un utilisateur par son ID", notes = "Cette methode permet de supprimer un utilisateur par son ID")
    public ResponseEntity delete(@PathVariable Long id) {

         utilisateurService.delete(id);
         return ResponseEntity.ok().build();
    }
    
    @GetMapping("/api/findUser/{email}")
    public UtilisateurDto findByMail(@PathVariable("email") String email) {
    	
    	UtilisateurDto user = utilisateurService.findByMail(email);
    	
    	return user;
    	
    }
    
    @PostMapping("/api/utilisateur/changerMdp")
    public UtilisateurDto changerPassword(@RequestBody ChangerPasswordUtilisateurDto changerPasswordUtilisateurDto) {
    	return utilisateurService.changerPassword(changerPasswordUtilisateurDto);
    }

}
