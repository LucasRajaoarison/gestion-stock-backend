package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.MouvementStckDto;
import com.kanto.gestiondestock.services.MouvementStockService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Api("/mouvementStocks")
public class MouvementStockController {

    @Autowired
    private MouvementStockService mouvementStockService;

    @GetMapping("/api/mouvement/stockReel/{idArticle}")
    public BigDecimal stockReelArticle(@PathVariable("idArticle") Long idArticle) {
       return mouvementStockService.stockReelArticle(idArticle);
    }

    @GetMapping("/api/mouvement/filtre/article/{idArticle}")
    public List<MouvementStckDto> mouvementStockArticle(@PathVariable("idArticle") Long idArticle) {
        return mouvementStockService.mouvementStockArticle(idArticle) ;
    }

    @PostMapping("/api/mouvement/entree")
    public MouvementStckDto entreStock(@RequestBody MouvementStckDto mouvementStckDto) {
        return mouvementStockService.entreStock(mouvementStckDto) ;
    }

    @PostMapping("/api/mouvement/sortie")
    public MouvementStckDto sortieStock(@RequestBody MouvementStckDto mouvementStckDto) {
        return mouvementStockService.sortieStock(mouvementStckDto) ;
    }

    @PostMapping("/api/mouvement/correctionPos")
    public MouvementStckDto correctionStockPositif(@RequestBody MouvementStckDto mouvementStckDto) {
        return mouvementStockService.correctionStockPositif(mouvementStckDto) ;
    }

    @PostMapping("/api/mouvement/correctionNeg")
    public MouvementStckDto correctionStockNegatif(@RequestBody MouvementStckDto mouvementStckDto) {
        return mouvementStockService.correctionStockNegatif(mouvementStckDto) ;
    }

}
