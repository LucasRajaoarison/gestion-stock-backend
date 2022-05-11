package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.DTO.MouvementStckDto;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.entity.MouvementStck;
import com.kanto.gestiondestock.entity.TypeMvtStck;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.repository.MouvementStckRepository;
import com.kanto.gestiondestock.services.ArticleService;
import com.kanto.gestiondestock.services.MouvementStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MouvementStockServiceImpl implements MouvementStockService {

    @Autowired
    private MouvementStckRepository  mouvementStckRepository;

    @Autowired
    private ArticleService articleService;


    @Override
    public BigDecimal stockReelArticle(Long idArticle) {

        if (idArticle == null) {
            log.warn("Article ID est null");
            return BigDecimal.valueOf(-1);
        }

        //juste pour verification, leve une exception si article n'existe pas
        articleService.findById(idArticle);

        return mouvementStckRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MouvementStckDto> mouvementStockArticle(Long idArticle) {
        return mouvementStckRepository.findAllByArticleId(idArticle).stream()
                .map(MouvementStckDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MouvementStckDto entreStock(MouvementStckDto mouvementStckDto) {

       return entreePositive(mouvementStckDto, TypeMvtStck.ENTREE);

    }

    @Override
    public MouvementStckDto sortieStock(MouvementStckDto mouvementStckDto) {

        return sortieNegative(mouvementStckDto, TypeMvtStck.SORTIE) ;
    }

    @Override
    public MouvementStckDto correctionStockPositif(MouvementStckDto mouvementStckDto) {

        return entreePositive(mouvementStckDto, TypeMvtStck.CORRECTION_POSITIF) ;
    }

    @Override
    public MouvementStckDto correctionStockNegatif(MouvementStckDto mouvementStckDto) {

        return sortieNegative(mouvementStckDto, TypeMvtStck.CORRECTION_NEGATIF) ;
    }



    private MouvementStckDto entreePositive(MouvementStckDto mouvementStckDto, TypeMvtStck typeMvtStck) {

        //
        //s'assurer d'avoir une quantite positif pour l'entree de stock
        //
        mouvementStckDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mouvementStckDto.getQuantite().doubleValue())
                )
        );

        mouvementStckDto.setTypeMvtStck(typeMvtStck);

        return MouvementStckDto.fromEntity(
                mouvementStckRepository.save(MouvementStckDto.toEntity(mouvementStckDto))
        );

    }

    private MouvementStckDto sortieNegative(MouvementStckDto mouvementStckDto, TypeMvtStck typeMvtStck) {

        //
        //s'assurer d'avoir une quantite "negatif" pour la sortie de stock
        //
        mouvementStckDto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mouvementStckDto.getQuantite().doubleValue()) * -1
                )
        );

        mouvementStckDto.setTypeMvtStck(typeMvtStck);

        return MouvementStckDto.fromEntity(
                mouvementStckRepository.save(MouvementStckDto.toEntity(mouvementStckDto))
        );

    }


}
