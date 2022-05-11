package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.entity.LigneCdeClient;
import com.kanto.gestiondestock.repository.LigneCdeClientRepository;
import com.kanto.gestiondestock.services.LigneCdeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LigneCdeClientServiceImpl implements LigneCdeClientService {

    @Autowired
    private LigneCdeClientRepository ligneCdeClientRepository;


    @Override
    public void save(LigneCommandeClientDto ligneCommandeClientDto) {
        ligneCdeClientRepository.save(LigneCommandeClientDto.toEntity(ligneCommandeClientDto)) ;
    }
}
