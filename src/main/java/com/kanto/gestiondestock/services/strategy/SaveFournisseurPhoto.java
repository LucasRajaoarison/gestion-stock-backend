package com.kanto.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.kanto.gestiondestock.DTO.EntrepriseDto;
import com.kanto.gestiondestock.DTO.FournisseurDto;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.services.EntrepriseService;
import com.kanto.gestiondestock.services.FlickrService;
import com.kanto.gestiondestock.services.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;


@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto> {

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private FournisseurService fournisseurService;

    @Override
    public FournisseurDto savePhoto(Long id, InputStream photo, String titre) throws FlickrException {
        FournisseurDto fournisseurDto = fournisseurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseurDto.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseurDto);
    }
}
