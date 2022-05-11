package com.kanto.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

//un type generic parce que quand on enregistre un objet, on doit le renvoyer
public interface Strategy<T> {

    T savePhoto(Long id, InputStream photo, String titre) throws FlickrException;

}
