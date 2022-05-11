package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.VenteDto;

import com.kanto.gestiondestock.services.VenteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("/ventes")
public class VenteController{

    @Autowired
    private VenteService venteService;

    @PostMapping(value = "/api/vente/create")
    @ApiOperation(value = "Enregistrer une vente (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier une vente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet vente creer/modifier")
    })
    public void save(@Valid @RequestBody VenteDto venteDto) {
        venteService.save(venteDto);
    }

    @GetMapping(value = "/api/vente/{id}")
    @ApiOperation(value = "Rechercher une vente par ID", notes = "Cette methode permet de chercher une vente par son ID", response = VenteDto.class)
    public VenteDto findById(@PathVariable Long id) {
        return venteService.findById(id);
    }

    @GetMapping(value = "/api/ventes")
    @ApiOperation(value = "Lister les ventes", notes = "Cette methode permet de lister les ventes", responseContainer = "List<VenteDto>")
    public List<VenteDto> findAll() {
        return venteService.findAll();
    }

    @DeleteMapping(value = "/api/vente/{id}")
    @ApiOperation(value = "Supprimer une vente par son ID", notes = "Cette methode permet de supprimer une vente par son ID")
    public void delete(@PathVariable Long id) {
        venteService.delete(id);
    }
}
