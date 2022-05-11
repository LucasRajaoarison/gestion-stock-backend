package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.LigneCommandeFournisseurDto;
import com.kanto.gestiondestock.repository.LigneCdeFournisseurRepository;
import com.kanto.gestiondestock.services.LigneCdeFournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class LigneCdeFournisseurServiceImpl implements LigneCdeFournisseurService {

    @Autowired
    private LigneCdeFournisseurRepository ligneCdeFournisseurRepository;

    @Override
    public void save(LigneCommandeFournisseurDto ligneCommandeFournisseurDto) {
            ligneCdeFournisseurRepository.save(LigneCommandeFournisseurDto.toEntity(ligneCommandeFournisseurDto)) ;
    }
}
