package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.Categorie;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.ArticleRepository;
import com.kanto.gestiondestock.repository.CategorieRepository;
import com.kanto.gestiondestock.services.CategorieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieRepository  categorieRepository ;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public CategorieDto save(CategorieDto categorieDto) {

       return CategorieDto.fromEntity(
               categorieRepository.save(CategorieDto.toEntity(categorieDto))
       );
    }

    @Override
    public CategorieDto findById(Long id) {

        if (id == null) {
            log.error("Category ID est null");
            return null;
        }

        Optional<Categorie> categ = Optional.of(categorieRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucun categorie avec ID : " + id, ErrorCodes.CATEGORY_NOT_FOUND)
        ));;


         return CategorieDto.fromEntity(categ.get());


    }

    @Override
    public CategorieDto findByCodeCategorie(String codeCategorie) {

        if (!StringUtils.hasLength(codeCategorie)){
            log.error("Code categorie est null");
            return null;
        }

        Categorie categorieByCode = categorieRepository.findByCode(codeCategorie);

        if ( categorieByCode != null) {
            return CategorieDto.fromEntity(categorieByCode);

        } else {
            throw new EntityNotFoundException("Aucun categorie avec ce code est trouver : " + codeCategorie, ErrorCodes.CATEGORY_NOT_FOUND);
        }

    }

    @Override
    public List<CategorieDto> findAll() {

        return categorieRepository.findAll().stream()
                .map(CategorieDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Categorie ID est null pour la delete");
            return;
        }

        List<Article> articles = articleRepository.findAllByCategorieId(id);
        if (!articles.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer une categorie deja assigner a une article",
                    ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }

        categorieRepository.deleteById(id);

    }
}
