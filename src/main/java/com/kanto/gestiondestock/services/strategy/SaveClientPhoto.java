package com.kanto.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.services.ClientService;
import com.kanto.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private ClientService clientService;

    @Override
    public ClientDto savePhoto(Long id, InputStream photo, String titre) throws FlickrException {
        ClientDto clientDto = clientService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clientDto.setPhoto(urlPhoto);
        return clientService.save(clientDto);
    }
}
