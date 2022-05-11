package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.services.ClientService;
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
@Api("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/api/client/create")
    @ApiOperation(value = "Enregistrer un client (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier un client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet client creer/modifier")
    })
    public void save(@Valid @RequestBody ClientDto clientDto) {


        if (clientDto.getAdresse().getAdresse1() == null && clientDto.getAdresse().getVille() == null) {
            log.error("Adresse est obligatoire");
            throw new EntityNotFoundException("Adresse est obligatoire");
        }

        clientService.save(clientDto);
    }

    @GetMapping(value = "/api/client/{id}")
    @ApiOperation(value = "Rechercher une client par ID", notes = "Cette methode permet de chercher une client par son ID", response = ClientDto.class)
    public ClientDto findById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @GetMapping(value = "/api/clients")
    @ApiOperation(value = "Lister les clients", notes = "Cette methode permet de lister les clients", responseContainer = "List<ClientDto>")
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @DeleteMapping(value = "/api/client/{id}")
    @ApiOperation(value = "Supprimer un client par son ID", notes = "Cette methode permet de supprimer un client par son ID")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
