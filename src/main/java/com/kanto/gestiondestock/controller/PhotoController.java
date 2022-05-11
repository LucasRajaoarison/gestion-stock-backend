package com.kanto.gestiondestock.controller;

import com.flickr4java.flickr.FlickrException;
import com.kanto.gestiondestock.services.strategy.StrategyPhotoContext;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api("/photos")
public class PhotoController {

    @Autowired
    private StrategyPhotoContext strategyPhotoContext;

    @PostMapping("/api/photos/{id}/{titre}/{context}")
    public Object savePhoto(String context, Long id, @RequestPart("file") MultipartFile photo, String titre) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), titre);
    }

}
