package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.DTO.MouvementStckDto;
import com.kanto.gestiondestock.DTO.VenteDto;

import java.math.BigDecimal;
import java.util.List;

public interface MouvementStockService {


    public BigDecimal stockReelArticle(Long idArticle) ;

    public List<MouvementStckDto> mouvementStockArticle(Long idArticle);

    public MouvementStckDto entreStock(MouvementStckDto mouvementStckDto);

    public MouvementStckDto sortieStock(MouvementStckDto mouvementStckDto);

    public MouvementStckDto correctionStockPositif(MouvementStckDto mouvementStckDto);

    public MouvementStckDto correctionStockNegatif(MouvementStckDto mouvementStckDto);

}
