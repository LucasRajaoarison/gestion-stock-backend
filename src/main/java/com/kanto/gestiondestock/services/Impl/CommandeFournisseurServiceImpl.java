package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.*;
import com.kanto.gestiondestock.entity.*;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.*;
import com.kanto.gestiondestock.services.CommandeFournisseurService;
import com.kanto.gestiondestock.services.MouvementStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;;

    @Autowired
    private LigneCdeFournisseurRepository ligneCdeFournisseurRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MouvementStockService mouvementStockService;


    @Override
    @Transactional
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {

        if (commandeFournisseurDto.getFournisseur().getId() == null) {
            log.error("fournisseur ID est obligatoire");
            throw new EntityNotFoundException("fournisseur ID est obligatoire");
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getFournisseur().getId()) ;
        if (fournisseur.isEmpty()) {
            log.warn("Fournisseur avec ID " + commandeFournisseurDto.getFournisseur().getId() + " est introuvable");
            throw new EntityNotFoundException("Fournisseur introuvable");
        }

            commandeFournisseurDto.getLigneCommandeFournisseur().forEach(ligne -> {
                if (ligne.getArticle().getId() != null) {
                    Optional<Article> article = articleRepository.findById(ligne.getArticle().getId()) ;
                    if (article.isEmpty()) {
                        log.warn("article introuvable");
                        throw new EntityNotFoundException("Article introuvable");
                    }
                }  else {
                    throw new EntityNotFoundException("Article ID est obligatoire");
                }

            });


        CommandeFournisseur savedCdeFournisseur = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto)) ;


            commandeFournisseurDto.getLigneCommandeFournisseur().forEach(ligneCde -> {

                if (ligneCde.getPrixUnitaire() != null && ligneCde.getQuantite() !=null) {

                    LigneCdeFournisseur ligneCdeFournisseur = LigneCommandeFournisseurDto.toEntity(ligneCde) ;
                    ligneCdeFournisseur.setCommandeFournisseur(savedCdeFournisseur);
                    ligneCdeFournisseurRepository.save(ligneCdeFournisseur);

                } else {
                    log.error("Prix unitaire et quantite sont obligatoire");
                    throw new EntityNotFoundException("Prix unitaire et quantite sont obligatoire");
                }

            });


        return CommandeFournisseurDto.fromEntity(savedCdeFournisseur) ;

    }

    @Override
    public CommandeFournisseurDto findById(Long id) {

        if (id == null) {
            log.error("CommandeFournisseur ID est null");
            return null;
        }

        Optional<CommandeFournisseur> commandeFournisseur = Optional.ofNullable(commandeFournisseurRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucun CommandeFournisseur avec ID " + id + "n'a ete trouver")));

        return CommandeFournisseurDto.fromEntity(commandeFournisseur.get());
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("CommandeFournisseur codeArticle est null");
            return null;
        }

        Optional<CommandeFournisseur> commandeFournisseur = Optional.ofNullable(commandeFournisseurRepository.findByCode(code)
                .orElseThrow(
                        () -> new EntityNotFoundException("Aucun CommandeFournisseur avec code : " + code + " n'a ete trouver")
                ));

        return CommandeFournisseurDto.fromEntity(commandeFournisseur.get());
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {

        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("CommandeFournisseur ID est null pour la delete");
            return;
        }
        List<LigneCdeFournisseur> ligneCdeFournisseurs = ligneCdeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCdeFournisseurs.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer une commande fournisseur deja assigner a une Ligne de commande",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }
        commandeFournisseurRepository.deleteById(id);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommade(Long idCommande, EtatCommande etatCommande) {

        checkIdCommande(idCommande);

        if (etatCommande == null) {
            log.error("Etat de la commande est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande car l'etat a fournir est null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        commandeFournisseurDto.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));

        //mettre a jour le stock si la commande est livree
        if(commandeFournisseurDto.isCommandeLivree()) {
            updateMouvementStock(idCommande);
        }

        return CommandeFournisseurDto.fromEntity(savedCommande) ;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Long idCommande, Long idFournisseur) {

        checkIdCommande(idCommande);

        if (idFournisseur == null) {
            log.error("Fournisseur ID est null");
            throw new InvalidOperationException("Impossible de modifier le fournisseur car l'ID a fournir est null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }


        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande) ;

        Optional<Fournisseur> fournisseurOptional = Optional.ofNullable(fournisseurRepository.findById(idFournisseur).orElseThrow(() ->
                new EntityNotFoundException("Aucun fournisseur avec ID : " + idFournisseur + " a ete trouve dans la BDD")
        ));

        commandeFournisseurDto.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto))
        );

    }

    @Override
    public CommandeFournisseurDto updateArticle(Long idCommande, Long idLigneCommande, Long idArticle) {

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        Optional<LigneCdeFournisseur> ligneCdeFournisseur = findLigneCommandeFournisseur(idLigneCommande) ;

        Optional<Article> articleOptional  = Optional.ofNullable(articleRepository.findById(idArticle).orElseThrow(
                () -> new EntityNotFoundException("Article avec ID " + idArticle + " est introuvable dans la BDD")
        ));

        LigneCdeFournisseur ligneCdeFournisseurToSaved = ligneCdeFournisseur.get();

        ligneCdeFournisseurToSaved.setArticle(articleOptional.get());

        ligneCdeFournisseurRepository.save(ligneCdeFournisseurToSaved);

        return commandeFournisseurDto;

    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Long idCommande, Long idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("Quantite est null ou egal a zero");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande car la quantite est null ou egal a 0",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        Optional<LigneCdeFournisseur> ligneCdeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCdeFournisseur ligneCdeFournisseur = ligneCdeFournisseurOptional.get();
        ligneCdeFournisseur.setQuantite(quantite);
        ligneCdeFournisseurRepository.save(ligneCdeFournisseur);

        return commandeFournisseurDto;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Long idCommande, Long idLigneCommande) {
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        //Juste pour checker  le ligne de commande fournisseur et informer le user au cas ou c'est absent
        findLigneCommandeFournisseur(idLigneCommande) ;
        ligneCdeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseurDto;
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByCommandeFournisseurId(Long idCommande) {
        return ligneCdeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }


    private void checkIdCommande(Long idCommande) {
        if (idCommande == null) {
            log.error("ComandeFournisseur ID est null");
            throw new InvalidOperationException("Impossible de modifier la commande car ID de la commande est null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Long idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("LigneCommande ID est null");
            throw new InvalidOperationException("Impossible de modifier la commande car l'ID de la ligne de commande est null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Long idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + "article est null");
            throw new InvalidOperationException("Impossible de modifier la commande avec un " + msg + " ID article",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }


    private CommandeFournisseurDto checkEtatCommande(Long idCommande) {

        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        if (commandeFournisseurDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande, Commande deja livree",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        return commandeFournisseurDto;
    }

    private Optional<LigneCdeFournisseur> findLigneCommandeFournisseur(Long idLigneCommande) {
        Optional<LigneCdeFournisseur> ligneCdeFournisseurOptional = Optional.of(ligneCdeFournisseurRepository.findById(idLigneCommande)
                .orElseThrow(
                        () -> new EntityNotFoundException("Ligne commande fournisseur introuvable dans la BDD")
                ));

        return ligneCdeFournisseurOptional;
    }

    private void updateMouvementStock(Long idCommande) {
        List<LigneCdeFournisseur> ligneCdeFournisseurs =  ligneCdeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCdeFournisseurs.forEach(ligne -> {
            MouvementStckDto stock = MouvementStckDto.builder()
                    .article(ArticleDto.fromEntity(ligne.getArticle()))
                    .dateMouvement(Instant.now())
                    .typeMvtStck(TypeMvtStck.ENTREE)
                    .sourceMvtStock(SourceMvtStock.COMMANDE_FOURNISSEUR)
                    .quantite(ligne.getQuantite())
                    .build();

            mouvementStockService.entreStock(stock);
        });

    }

}
