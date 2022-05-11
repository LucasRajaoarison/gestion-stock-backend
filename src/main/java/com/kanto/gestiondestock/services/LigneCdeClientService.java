package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.entity.LigneCdeClient;

public interface LigneCdeClientService {

    public void save(LigneCommandeClientDto ligneCommandeClientDto);

}
