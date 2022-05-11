package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.*;
import com.kanto.gestiondestock.entity.*;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidEntityException;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.ArticleRepository;
import com.kanto.gestiondestock.repository.LigneVenteRepository;
import com.kanto.gestiondestock.repository.VenteRepository;
import com.kanto.gestiondestock.services.MouvementStockService;
import com.kanto.gestiondestock.services.VenteService;
import com.kanto.gestiondestock.validators.FournisseurValidator;
import com.kanto.gestiondestock.validators.VenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VenteServiceImpl implements VenteService {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MouvementStockService mouvementStockService;


    @Override
    public void save(VenteDto venteDto) {

        venteDto.getLigneVentes().forEach(ligneVenteDto -> {

            if (ligneVenteDto.getArticle().getId() != null) {
                Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId()) ;
                if (article.isEmpty()) {
                    log.warn("article pour la vente n'est pas disponible dans la BDD");
                    throw new EntityNotFoundException("Article inexistant dans la BDD");
                }
            } else {
                throw new EntityNotFoundException("Article ID est obligatoire");
            }


        });

        Vente savedVente = venteRepository.save(VenteDto.toEntity(venteDto));

            venteDto.getLigneVentes().forEach(ligne -> {

                if (ligne.getPrixUnitaire() != null && ligne.getQuantite() !=null) {

                    LigneVente ligneVente = LigneVenteDto.toEntity(ligne) ;
                    ligneVente.setVente(savedVente);
                    ligneVenteRepository.save(ligneVente) ;

                    //mettre a jour le stock
                    updateMouvementStock(ligneVente);
                } else {

                    log.error("Prix unitaire et quantite sont obligatoire");
                    throw new EntityNotFoundException("Prix unitaire et quantite sont obligatoire");
                }

            });


    }

    @Override
    public VenteDto findById(Long id) {

        if (id == null) {
            log.error("Vente ID est null");
            return null;
        }

        Optional<Vente> vente = Optional.ofNullable(venteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucune vente avec ID : " + id, ErrorCodes.VENTE_NOT_FOUND)
        ));;

        VenteDto venteDto = VenteDto.fromEntity(vente.get());

        return venteDto ;

    }

    @Override
    public List<VenteDto> findAll() {

        return venteRepository.findAll().stream()
                .map(VenteDto::fromEntity)
                .collect(Collectors.toList());


    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Vente ID est null pour la delete");
            return;
        }
        List<LigneVente>  ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer une vente deja assigner dans une ligne vente",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        venteRepository.deleteById(id);

    }


    private void updateMouvementStock(LigneVente ligneVente) {

            MouvementStckDto stock = MouvementStckDto.builder()
                    .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                    .dateMouvement(Instant.now())
                    .typeMvtStck(TypeMvtStck.SORTIE)
                    .sourceMvtStock(SourceMvtStock.VENTE)
                    .quantite(ligneVente.getQuantite())
                    .build();

            mouvementStockService.sortieStock(stock);


    }
}
