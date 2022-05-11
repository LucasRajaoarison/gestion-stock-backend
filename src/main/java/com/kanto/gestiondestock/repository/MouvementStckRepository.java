package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.MouvementStck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MouvementStckRepository extends JpaRepository<MouvementStck, Long> {

    @Query("select sum(m.quantite) from MouvementStck m where m.article.id = :idArticle")
    public BigDecimal stockReelArticle(@Param("idArticle") Long idArticle) ;

    public List<MouvementStck> findAllByArticleId(Long id);

}
