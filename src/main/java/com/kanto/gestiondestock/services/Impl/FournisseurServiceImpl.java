package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.FournisseurDto;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.entity.CommandeFournisseur;
import com.kanto.gestiondestock.entity.Fournisseur;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.CommandeFournisseurRepository;
import com.kanto.gestiondestock.repository.FournisseurRepository;
import com.kanto.gestiondestock.services.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Override
    public FournisseurDto save(FournisseurDto fournisseurDto) {

        return FournisseurDto.fromEntity(
                fournisseurRepository.save(FournisseurDto.toEntity(fournisseurDto))
        );


    }

    @Override
    public FournisseurDto findById(Long id) {

        if (id == null) {
            log.error("Fournisseur ID est null");
            return null;
        }

        Optional<Fournisseur> fournisseur = Optional.ofNullable(fournisseurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucun fournisseur avec ID : " + id, ErrorCodes.FOURNISSEUR_NOT_FOUND)
        ));;

        FournisseurDto fournisseurDto = FournisseurDto.fromEntity(fournisseur.get());

        return fournisseurDto ;

    }

    @Override
    public List<FournisseurDto> findAll() {

        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Fournisseur ID est null pour la delete");
            return;
        }
        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseurs.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer un fournisseur deja assigner dans une commande fournisseur",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }
        fournisseurRepository.deleteById(id);

    }
}
