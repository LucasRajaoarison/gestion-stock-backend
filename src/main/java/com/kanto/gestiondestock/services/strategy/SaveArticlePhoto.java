package com.kanto.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.services.ArticleService;
import com.kanto.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.InvalidClassException;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private ArticleService articleService;

    @Override
    public ArticleDto savePhoto(Long id, InputStream photo, String titre) throws FlickrException {
        ArticleDto articleDto = articleService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDto.setPhoto(urlPhoto);
        return articleService.save(articleDto);
    }
}
