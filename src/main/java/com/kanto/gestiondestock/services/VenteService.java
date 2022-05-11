package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.FournisseurDto;
import com.kanto.gestiondestock.DTO.VenteDto;

import java.util.List;

public interface VenteService {

    public void save(VenteDto venteDto);

    public VenteDto findById(Long id);

    public List<VenteDto> findAll();

    public void delete(Long id);

}
