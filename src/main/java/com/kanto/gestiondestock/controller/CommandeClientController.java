package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.CommandeClientDto;

import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.entity.*;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.services.ArticleService;
import com.kanto.gestiondestock.services.ClientService;
import com.kanto.gestiondestock.services.CommandeClientService;
import com.kanto.gestiondestock.services.LigneCdeClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
@Api("/commandeClients")
public class CommandeClientController {

    @Autowired
    private CommandeClientService commandeClientService;


    @PostMapping(value = "/api/commandeClient/create")
    @ApiOperation(value = "Enregistrer une commande client (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier une commande client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet commande client creer/modifier")
    })
    public ResponseEntity<CommandeClientDto> save(@Valid @RequestBody CommandeClientDto commandeClientDto) {

        // return ResponseEntity.status(HttpStatus.OK).body(commandeClientService.save(commandeClientDto)) ;

        return ResponseEntity.ok(commandeClientService.save(commandeClientDto)) ;

    }

    @GetMapping(value = "/api/commandeClient/{id}")
    @ApiOperation(value = "Rechercher une commande client par ID", notes = "Cette methode permet de chercher une commande client par son ID", response = CommandeClientDto.class)
    public ResponseEntity<CommandeClientDto> findById(@PathVariable Long id) {
        return  ResponseEntity.ok(commandeClientService.findById(id));
    }

    @GetMapping(value = "/api/commandeClients")
    @ApiOperation(value = "Lister les commande clients", notes = "Cette methode permet de lister les commande clients", responseContainer = "List<CommandeClientDto>")
    public ResponseEntity<List<CommandeClientDto>> findAll() {
        return ResponseEntity.ok(commandeClientService.findAll());
    }

    @DeleteMapping(value = "/api/commandeClient/{id}")
    @ApiOperation(value = "Supprimer une commande client par son ID", notes = "Cette methode permet de supprimer une commande client par son ID")
    public ResponseEntity delete(@PathVariable Long id) {
        commandeClientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api/commandeClient/update/etat/{idCommande}/{etatCommande}")
    public ResponseEntity<CommandeClientDto> updateEtatCommade(@PathVariable("idCommande") Long idCommande,
                                               @PathVariable("etatCommande") EtatCommande etatCommande) {

        return ResponseEntity.ok(commandeClientService.updateEtatCommade(idCommande, etatCommande)) ;

    }

    @PatchMapping("/api/commandeClient/update/quantite/{idCommande}/{idLigneCommande}/{etatCommande}")
    public ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Long idCommande,
                                                                    @PathVariable("idLigneCommande")Long idLigneCommande,
                                                                    @PathVariable("quantite") BigDecimal quantite) {

        return ResponseEntity.ok(
                commandeClientService.updateQuantiteCommande(idCommande, idLigneCommande, quantite)
        );

    }

    @PatchMapping("/api/commandeClient/update/client/{idCommande}/{idClient}")
    public ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Long idCommande,
                                                          @PathVariable("idClient")Long idClient) {

        return ResponseEntity.ok(
                commandeClientService.updateClient(idCommande, idClient)
        ) ;

    }

    @PatchMapping("/api/commandeClient/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    public ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande") Long idCommande,
                                                           @PathVariable("idLigneCommande") Long idLigneCommande,
                                                           @PathVariable("idArticle") Long idArticle) {

        return ResponseEntity.ok(
                commandeClientService.updateArticle(idCommande, idLigneCommande, idArticle)
        );

    }

    @DeleteMapping("/api/commandeClient/delete/client/{idCommande}/{idLigneCommande}")
    public ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande") Long idCommande,
                                                           @PathVariable("idLigneCommande")  Long idLigneCommande) {

        return ResponseEntity.ok(
                commandeClientService.deleteArticle(idCommande, idLigneCommande)
        ) ;
    }

    @GetMapping("/api/commandeClient/ligneCommande/{idCommande}")
    public ResponseEntity<List<LigneCommandeClientDto>> findAllLigneCommandeClientByCommandeClientId(@PathVariable("idCommande") Long idCommande) {

        return ResponseEntity.ok(
                commandeClientService.findAllLigneCommandeClientByCommandeClientId(idCommande)
        ) ;
    }

}
