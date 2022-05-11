package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.CommandeClientDto;
import com.kanto.gestiondestock.DTO.EntrepriseDto;
import com.kanto.gestiondestock.DTO.RoleDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.entity.Entreprise;
import com.kanto.gestiondestock.entity.Utilisateur;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidEntityException;
import com.kanto.gestiondestock.repository.EntrepriseRepository;
import com.kanto.gestiondestock.repository.RoleRepository;
import com.kanto.gestiondestock.repository.UtilisateurRepository;
import com.kanto.gestiondestock.services.EntrepriseService;
import com.kanto.gestiondestock.services.UtilisateurService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@Builder
public class EntrepriseServiceImpl implements EntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private UtilisateurService utilisateurService;
    
    @Autowired
    private UtilisateurRepository repository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {
    	
    	if (userAlreadyExists(entrepriseDto.getMail())) {
    		log.error("Entreprise Email already exists");
            throw new InvalidEntityException("Entreprise Email already exists", ErrorCodes.ENTREPRISE_ALREADY_IN_USE);
        }

       EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
               entrepriseRepository.save(EntrepriseDto.toEntity(entrepriseDto))
       ) ;


        //C'est un utilisateur qui cree un entreprise donc...
       UtilisateurDto user = fromEntreprise(savedEntreprise);
       user.setRoles(Arrays.asList(RoleDto.fromEntity(
               roleRepository.findByRoleName("ADMIN"))
       ));

       UtilisateurDto savedUtilisateur = utilisateurService.save(user) ;

       return savedEntreprise;

    }

    @Override
    public EntrepriseDto findById(Long id) {

        if (id == null) {
            log.error("Entreprise ID est null");
            return null;
        }

        Optional<Entreprise> entreprise = Optional.ofNullable(entrepriseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucune entreprise avec ID : " + id + " n'a ete trouver")));

        return EntrepriseDto.fromEntity(entreprise.get());


    }


    @Override
    public List<EntrepriseDto> findAll() {

        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());


    }

    @Override
    public void delete(Long id) {


        if (id == null) {
            log.error("Entreprise ID est null pour la delete");
            return;
        }
        entrepriseRepository.deleteById(id);


    }

    private UtilisateurDto fromEntreprise(EntrepriseDto entrepriseDto)  {

        return UtilisateurDto.builder()
             //   .adresse(entrepriseDto.getAdresse())
                .nom(entrepriseDto.getNom())
                .prenom(entrepriseDto.getCodeFiscal())
                .mail(entrepriseDto.getMail())
                .password(generateRandomPassword())
                .entreprise(entrepriseDto)
                .dateDeNaissance(Instant.now())
                .photo(entrepriseDto.getPhoto())
                .build();

    }

    private String generateRandomPassword() {
        return "som3R@ndOmP@$$word";
    }

    private boolean userAlreadyExists(String email) {
        Optional<Entreprise> user = Optional.ofNullable(entrepriseRepository.findByMail(email));
        return user.isPresent();
    }

    
}
