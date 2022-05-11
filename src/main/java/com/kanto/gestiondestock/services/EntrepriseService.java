package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.DTO.EntrepriseDto;
import com.kanto.gestiondestock.entity.Entreprise;

import java.util.List;
import java.util.Optional;

public interface EntrepriseService {

    public EntrepriseDto save(EntrepriseDto entrepriseDto);

    public EntrepriseDto findById(Long id);

    public List<EntrepriseDto> findAll();

    public void delete(Long id);

}
