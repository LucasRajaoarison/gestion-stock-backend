package com.kanto.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.DTO.EntrepriseDto;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.services.EntrepriseService;
import com.kanto.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;


@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private EntrepriseService entrepriseService;

    @Override
    public EntrepriseDto savePhoto(Long id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entrepriseDto = entrepriseService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entrepriseDto.setPhoto(urlPhoto);
        return entrepriseService.save(entrepriseDto);
    }
}
