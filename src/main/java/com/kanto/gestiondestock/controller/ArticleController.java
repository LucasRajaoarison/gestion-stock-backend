package com.kanto.gestiondestock.controller;

import com.kanto.gestiondestock.DTO.ArticleDto;
import com.kanto.gestiondestock.DTO.LigneCommandeClientDto;
import com.kanto.gestiondestock.DTO.LigneCommandeFournisseurDto;
import com.kanto.gestiondestock.DTO.LigneVenteDto;
import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.Categorie;
import com.kanto.gestiondestock.entity.Entreprise;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.services.ArticleService;
import com.kanto.gestiondestock.services.CategorieService;
import com.kanto.gestiondestock.services.EntrepriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Api("/articles")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private EntrepriseService entrepriseService;

    @Autowired
    private CategorieService categorieService;

   /* @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }*/


    @PostMapping(value = "/api/article/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Enregistrer un article (Ajouter/Modifier)", notes = "Cette methode permet d'enregister ou de modifier un article")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet article creer/modifier")
    })
    public ArticleDto save(@Valid @RequestBody ArticleDto articleDto) {

        if (articleDto.getCategorie().getId() == null) {
            log.error("categorie ID est obligatoire");
            throw new EntityNotFoundException("categorie ID est obligatoire");
        }
        if (articleDto.getEntreprise().getId() == null) {
            log.error("Entreprise ID est obligatoire");
            throw new EntityNotFoundException("Entreprise ID est obligatoire");
        }

        ArticleDto savedArticle = articleService.save(articleDto);
        
       return savedArticle ;

    }

    @GetMapping(value = "/api/article/{articleId}")
    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet de chercher un article par son ID", response = ArticleDto.class)
    public ArticleDto findById(@PathVariable("articleId") Long Id) {

        return articleService.findById(Id);
    }

    @GetMapping(value = "/api/article/code/{code}")
    @ApiOperation(value = "Rechercher un article par code", notes = "Cette methode permet de chercher un article par son code", response = ArticleDto.class)
    public ArticleDto findByCodeArticle(@PathVariable("code") String codeArticle){
        return articleService.findByCodeArticle(codeArticle);
    }

    @GetMapping(value = "/api/articles")
    @ApiOperation(value = "Lister les articles", notes = "Cette methode permet de lister les articles", responseContainer = "List<ArticleDto>")
    public List<ArticleDto> findAll() {

        return articleService.findAll();
    }

    @DeleteMapping(value = "/api/article/{id}")
    @ApiOperation(value = "Supprimer un article par son ID", notes = "Cette methode permet de supprimer un article par son ID")
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }


    @GetMapping(value = "/api/articles/historique/vente/{idArticle}")
    public ResponseEntity<List<LigneVenteDto>> findHistoriqueVente(@PathVariable("idArticle") Long idArticle){

        return ResponseEntity.ok(
                articleService.findHistoriqueVente(idArticle)
        );
    }

    @GetMapping(value = "/api/articles/historique/commandeclient/{idArticle}")
    public ResponseEntity<List<LigneCommandeClientDto>> findHistoriqueCommandeClient(@PathVariable("idArticle") Long idArticle){

        return ResponseEntity.ok(
                articleService.findHistoriqueCommandeClient(idArticle)
        );
    }

    @GetMapping(value = "/api/articles/historique/commandefournisseur/{idArticle}")
    public ResponseEntity<List<LigneCommandeFournisseurDto>> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Long idArticle){

        return ResponseEntity.ok(
                articleService.findHistoriqueCommandeFournisseur(idArticle)
        );
    }

    @GetMapping(value = "/api/articles/filter/category/{idCategory}")
    public ResponseEntity<List<ArticleDto>> findAllArticleByCategoryId(@PathVariable("idCategory") Long idCategory){

        return ResponseEntity.ok(
                articleService.findAllArticleByCategoryId(idCategory)
        );
    }
}
