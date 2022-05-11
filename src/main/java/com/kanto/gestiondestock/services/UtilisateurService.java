package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.ChangerPasswordUtilisateurDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import com.kanto.gestiondestock.DTO.VenteDto;

import java.util.List;

public interface UtilisateurService {

    public UtilisateurDto save(UtilisateurDto utilisateurDto);

    public UtilisateurDto findById(Long id);

    public List<UtilisateurDto> findAll();

    public void delete(Long id);

    public UtilisateurDto changerPassword(ChangerPasswordUtilisateurDto changerPasswordUtilisateurDto);
    
    public UtilisateurDto findByMail(String email);
}
