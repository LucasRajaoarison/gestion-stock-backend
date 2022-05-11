package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.EntrepriseDto;
import com.kanto.gestiondestock.DTO.FournisseurDto;

import java.util.List;

public interface FournisseurService {

    public FournisseurDto save(FournisseurDto fournisseurDto);

    public FournisseurDto findById(Long id);

    public List<FournisseurDto> findAll();

    public void delete(Long id);

}
