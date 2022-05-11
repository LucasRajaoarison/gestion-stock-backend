package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.entity.Categorie;

import java.util.List;
import java.util.Optional;

public interface CategorieService {

    public CategorieDto save(CategorieDto categorieDto);

    public CategorieDto findById(Long id);

    public CategorieDto findByCodeCategorie(String codeCategorie);

    public List<CategorieDto> findAll();

    public void delete(Long id);


}
