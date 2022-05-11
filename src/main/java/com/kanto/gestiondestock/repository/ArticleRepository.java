package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByCodeArticle(String code);

    public List<Article> findAllByCategorieId(Long id);

   // @Query()
   // List<Article> findByCustomQuery();

}
