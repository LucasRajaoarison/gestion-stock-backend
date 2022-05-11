package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.entity.Client;

import java.util.List;

public interface ClientService {

    public ClientDto save(ClientDto clientDto);

    public ClientDto findById(Long id);

    public List<ClientDto> findAll();

    public void delete(Long id);


}
