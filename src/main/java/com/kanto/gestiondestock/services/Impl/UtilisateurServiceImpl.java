package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.ChangerPasswordUtilisateurDto;
import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.entity.Utilisateur;
import com.kanto.gestiondestock.entity.auth.ExtendedUser;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidEntityException;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.UtilisateurRepository;
import com.kanto.gestiondestock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {

        if (userAlreadyExists(utilisateurDto.getMail())) {
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILLISATEUR_ALREADY_IN_USE);
        }

        String hashPWD = bCryptPasswordEncoder.encode(utilisateurDto.getPassword());
        utilisateurDto.setPassword(hashPWD);

        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(UtilisateurDto.toEntity(utilisateurDto))
        ) ;
    }

    @Override
    public UtilisateurDto findById(Long id) {

        if (id == null) {
            log.error("Utilisateur ID est null");
            return null;
        }

        Optional<Utilisateur>  utilisateur = Optional.ofNullable(utilisateurRepository.findById(id)
                .orElseThrow(
                () -> new EntityNotFoundException("Aucun utilisateur avec ID : " + id + " n'a ete trouver")
                )
        );;

        return UtilisateurDto.fromEntity(utilisateur.get());
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Utilisateur ID est null pour la delete");
            return;
        }
        utilisateurRepository.deleteById(id);

    }

    @Override
    public UtilisateurDto changerPassword(ChangerPasswordUtilisateurDto dto) {

      validate(dto);

      Optional<Utilisateur> utilisateurOptional  = utilisateurRepository.findById(dto.getId()) ;

      if (utilisateurOptional.isEmpty()) {
          log.warn("Aucun utilisateur n'a ete trouve avec l'ID : " + dto.getId());
          throw new EntityNotFoundException("Aucun utilisateur  n'a ete trouve dans la BDD",
                  ErrorCodes.UTILISATEUR_NOT_FOUND) ;
      }

      Utilisateur utilisateur = utilisateurOptional.get() ;
      utilisateur.setPassword(dto.getPassword());

        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }

    
    
    @Override
	public UtilisateurDto findByMail(String email) {
    	 if (email == null) {
             log.error("Utilisateur mail est null");
             return null;
         }

         Optional<Utilisateur>  utilisateur = Optional.of(utilisateurRepository.findByMail(email));
         if( !utilisateur.isPresent()) {
        	 throw new EntityNotFoundException("Aucun utilisateur avec email : " + email + " n'a ete trouver") ;
         }
      

         return UtilisateurDto.fromEntity(utilisateur.get());
	}
    

    private boolean userAlreadyExists(String email) {
        Optional<Utilisateur> user = Optional.ofNullable(utilisateurRepository.findByMail(email));
        return user.isPresent();
    }

    private void validate(ChangerPasswordUtilisateurDto dto) {

        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet null");
            throw new InvalidOperationException("Aucune information a ete fournie pour changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if (dto.getId() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID null");
            throw new InvalidOperationException("ID utilisateur null, impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if (StringUtils.hasLength(dto.getPassword()) || StringUtils.hasLength(dto.getConfirmPassword())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe null");
            throw new InvalidOperationException("Mot de passe utilisateur null, impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            log.warn("Impossible de modifier le mot de passe avec deux mot de passe different");
            throw new InvalidOperationException("Mot de passe utilisateur non conformes, impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }


    }

	

}
