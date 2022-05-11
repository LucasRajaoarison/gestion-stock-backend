package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.CommandeClientDto;
import com.kanto.gestiondestock.DTO.CommandeFournisseurDto;
import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.DTO.LigneCommandeFournisseurDto;
import com.kanto.gestiondestock.entity.EtatCommande;
import com.kanto.gestiondestock.services.ArticleService;
import com.kanto.gestiondestock.services.ClientService;
import com.kanto.gestiondestock.services.CommandeFournisseurService;
import com.kanto.gestiondestock.services.LigneCdeFournisseurService;
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
@Api("/commandeFournisseurs")
public class CommandeFournisseurController {

    @Autowired
    private CommandeFournisseurService commandeFournisseurService;


    @PostMapping(value = "/api/commandeFournisseur/create")
    @ApiOperation(value = "Enregistrer une commande fournisseur (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier une commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet commande fournisseur creer/modifier")
    })
    public ResponseEntity<CommandeFournisseurDto> save(@Valid @RequestBody CommandeFournisseurDto commandeFournisseurDto) {

        // return ResponseEntity.status(HttpStatus.OK).body(commandeFournisseurService.save(commandeClientDto)) ;

        return ResponseEntity.ok(commandeFournisseurService.save(commandeFournisseurDto)) ;

    }

    @GetMapping(value = "/api/commandeFournisseur/{id}")
    @ApiOperation(value = "Rechercher une commande fournisseur par ID", notes = "Cette methode permet de chercher une commande fournisseur par son ID", response = CommandeFournisseurDto.class)
    public ResponseEntity<CommandeFournisseurDto> findById(@PathVariable Long id) {
        return  ResponseEntity.ok(commandeFournisseurService.findById(id));
    }

    @GetMapping(value = "/api/commandeFournisseurs")
    @ApiOperation(value = "Lister les commande fournisseurs", notes = "Cette methode permet de lister les commande fournisseurs", responseContainer = "List<CommandeFournisseurDto>")
    public ResponseEntity<List<CommandeFournisseurDto>> findAll() {
        return ResponseEntity.ok(commandeFournisseurService.findAll());
    }

    @DeleteMapping(value = "/api/commandeFournisseur/{id}")
    @ApiOperation(value = "Supprimer une commande fournisseur par son ID", notes = "Cette methode permet de supprimer une commande fournisseur par son ID")
    public ResponseEntity delete(@PathVariable Long id) {
        commandeFournisseurService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api/commandeFournisseur/update/etat/{idCommande}/{etatCommande}")
    public ResponseEntity<CommandeFournisseurDto> updateEtatCommade(@PathVariable("idCommande") Long idCommande,
                                                               @PathVariable("etatCommande") EtatCommande etatCommande) {

        return ResponseEntity.ok(commandeFournisseurService.updateEtatCommade(idCommande, etatCommande)) ;

    }

    @PatchMapping("/api/commandeFournisseur/update/quantite/{idCommande}/{idLigneCommande}/{etatCommande}")
    public ResponseEntity<CommandeFournisseurDto> updateQuantiteCommande(@PathVariable("idCommande") Long idCommande,
                                                                    @PathVariable("idLigneCommande")Long idLigneCommande,
                                                                    @PathVariable("quantite") BigDecimal quantite) {

        return ResponseEntity.ok(
                commandeFournisseurService.updateQuantiteCommande(idCommande, idLigneCommande, quantite)
        );

    }

    @PatchMapping("/api/commandeFournisseur/update/fournisseur/{idCommande}/{idFournisseur}")
    public ResponseEntity<CommandeFournisseurDto> updateClient(@PathVariable("idCommande") Long idCommande,
                                                          @PathVariable("idFournisseur")Long idFournisseur) {

        return ResponseEntity.ok(
                commandeFournisseurService.updateFournisseur(idCommande, idFournisseur)
        ) ;

    }

    @PatchMapping("/api/commandeFournisseur/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    public ResponseEntity<CommandeFournisseurDto> updateArticle(@PathVariable("idCommande") Long idCommande,
                                                           @PathVariable("idLigneCommande") Long idLigneCommande,
                                                           @PathVariable("idArticle") Long idArticle) {

        return ResponseEntity.ok(
                commandeFournisseurService.updateArticle(idCommande, idLigneCommande, idArticle)
        );

    }

    @DeleteMapping("/api/commandeFournisseur/delete/fournisseur/{idCommande}/{idLigneCommande}")
    public ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable("idCommande") Long idCommande,
                                                           @PathVariable("idLigneCommande")  Long idLigneCommande) {

        return ResponseEntity.ok(
                commandeFournisseurService.deleteArticle(idCommande, idLigneCommande)
        ) ;
    }

    @GetMapping("/api/commandeFournisseur/ligneCommande/{idCommande}")
    public ResponseEntity<List<LigneCommandeFournisseurDto>> findAllLigneCommandeClientByCommandeClientId(@PathVariable("idCommande") Long idCommande) {

        return ResponseEntity.ok(
                commandeFournisseurService.findAllLigneCommandeFournisseurByCommandeFournisseurId(idCommande)
        ) ;
    }

}
