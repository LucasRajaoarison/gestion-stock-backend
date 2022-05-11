package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.*;
import com.kanto.gestiondestock.entity.*;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidEntityException;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.ArticleRepository;
import com.kanto.gestiondestock.repository.ClientRepository;
import com.kanto.gestiondestock.repository.CommandeClientRepository;
import com.kanto.gestiondestock.repository.LigneCdeClientRepository;
import com.kanto.gestiondestock.services.CommandeClientService;
import com.kanto.gestiondestock.services.MouvementStockService;
import com.kanto.gestiondestock.validators.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.dnd.InvalidDnDOperationException;
import java.math.BigDecimal;
import java.nio.charset.CoderMalfunctionError;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CommandeClientServiceImpl implements CommandeClientService {

    @Autowired
    private CommandeClientRepository commandeClientRepository;;

    @Autowired
    private LigneCdeClientRepository ligneCdeClientRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MouvementStockService mouvementStockService;

    @Override
    @Transactional
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {

        if (commandeClientDto.getId() != null && commandeClientDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande, Commande deja livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }


        if (commandeClientDto.getClient().getId() == null) {
            log.error("client ID est obligatoire");
            throw new EntityNotFoundException("client ID est obligatoire");
        }

        Optional<Client>  client = clientRepository.findById(commandeClientDto.getClient().getId()) ;
        if (client.isEmpty()) {
            log.warn("Client avec ID " + commandeClientDto.getClient().getId() + " est introuvable");
            throw new EntityNotFoundException("Client introuvable dans la BDD");
        }

            commandeClientDto.getLigneCdeClients().forEach(ligne -> {

                if (ligne.getArticle().getId() != null) {
                    Optional<Article> article = articleRepository.findById(ligne.getArticle().getId()) ;
                    if (article.isEmpty()) {
                        log.warn("article introuvable");
                        throw new EntityNotFoundException("Article introuvable");
                    }
                } else {
                    throw new EntityNotFoundException("Article ID est obligatoire");
                }

            });


        CommandeClient savedCdeClient = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto)) ;

           commandeClientDto.getLigneCdeClients().forEach(ligneCde -> {

               if (ligneCde.getPrixUnitaire() != null && ligneCde.getQuantite() !=null) {

                   LigneCdeClient ligneCdeClient = LigneCommandeClientDto.toEntity(ligneCde) ;
                   ligneCdeClient.setCommandeClient(savedCdeClient);
                   ligneCdeClientRepository.save(ligneCdeClient);
               } else {
                   log.error("Prix unitaire et quantite sont obligatoire");
                   throw new EntityNotFoundException("Prix unitaire et quantite sont obligatoire");
               }

           });


       return CommandeClientDto.fromEntity(savedCdeClient) ;

    }


    @Override
    public CommandeClientDto findById(Long id) {
        if (id == null) {
            log.error("CommandeClient ID est null");
            return null;
        }

        Optional<CommandeClient> commandeClient = Optional.of(commandeClientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucun commandeClient avec ID : " + id + " n'a ete trouver")));

        return CommandeClientDto.fromEntity(commandeClient.get());
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("CommandeClient codeArticle est null");
            return null;
        }

        Optional<CommandeClient> commandeClient = Optional.ofNullable(commandeClientRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Aucun commandeClient avec code :" + code + " n'a ete trouver")));;

        return CommandeClientDto.fromEntity(commandeClient.get());
    }

    @Override
    public List<CommandeClientDto> findAll() {

        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("comandeClient ID est null pour la delete");
            return;
        }

        List<LigneCdeClient> ligneCdeClients = ligneCdeClientRepository.findAllByCommandeClientId(id);
        if (!ligneCdeClients.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer une commande client deja assigner a une Ligne de commande",
                    ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
        }
        commandeClientRepository.deleteById(id);
    }



    @Override
    public CommandeClientDto updateEtatCommade(Long idCommande, EtatCommande etatCommande) {

        checkIdCommande(idCommande);

        if (etatCommande == null) {
            log.error("Etat de la commande est null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande car l'etat a fournir est null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        commandeClient.setEtatCommande(etatCommande);

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));


        //mettre a jour le stock si la commande est livree
        if(commandeClient.isCommandeLivree()) {

            updateMouvementStock(idCommande);
        }


        return CommandeClientDto.fromEntity(savedCmdClt) ;

    }




    @Override
    public CommandeClientDto updateQuantiteCommande(Long idCommande, Long idLigneCommande, BigDecimal quantite) {

        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("Quantite est null ou egal a zero");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande car la quantite est null ou egal a 0",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCdeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

        LigneCdeClient ligneCdeClient = ligneCommandeClientOptional.get();
        ligneCdeClient.setQuantite(quantite);
        ligneCdeClientRepository.save(ligneCdeClient);

        return commandeClient;

    }



    @Override
    public CommandeClientDto updateClient(Long idCommande, Long idClient) {

        checkIdCommande(idCommande);

        if (idClient == null) {
            log.error("Client ID est null");
            throw new InvalidOperationException("Impossible de modifier le client car l'ID a fournir est null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }


        CommandeClientDto commandeClient = checkEtatCommande(idCommande) ;

        Optional<Client> clientOptional = Optional.ofNullable(clientRepository.findById(idClient).orElseThrow(() ->
                new EntityNotFoundException("Aucun client avec ID : " + idClient + " a ete trouve dans la BDD")
        ));

        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );

    }

    @Override
    public CommandeClientDto updateArticle(Long idCommande, Long idLigneCommande, Long idArticle) {

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);

        Optional<LigneCdeClient> ligneCdeClient = findLigneCommandeClient(idLigneCommande) ;

        Optional<Article> articleOptional  = Optional.ofNullable(articleRepository.findById(idArticle).orElseThrow(
                () -> new EntityNotFoundException("Article avec ID " + idArticle + " est introuvable dans la BDD")
        ));

        LigneCdeClient ligneCdeClientToSaved = ligneCdeClient.get();

        ligneCdeClientToSaved.setArticle(articleOptional.get());

        ligneCdeClientRepository.save(ligneCdeClientToSaved);

        return commandeClientDto;
    }

    @Override
    public CommandeClientDto deleteArticle(Long idCommande, Long idLigneCommande) {

        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClientDto = checkEtatCommande(idCommande);

        //Juste pour checker  le ligne de commande client et informer le user au cas ou c'est absent
        findLigneCommandeClient(idLigneCommande) ;
        ligneCdeClientRepository.deleteById(idLigneCommande);

        return commandeClientDto;
    }

    @Override
    public List<LigneCommandeClientDto> findAllLigneCommandeClientByCommandeClientId(Long idCommande) {
        return ligneCdeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }


    private void checkIdCommande(Long idCommande) {
        if (idCommande == null) {
            log.error("comandeClient ID est null");
            throw new InvalidOperationException("Impossible de modifier la commande car ID de la commande est null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Long idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("LigneCommande ID est null");
            throw new InvalidOperationException("Impossible de modifier la commande car l'ID de la ligne de commande est null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Long idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + "article est null");
            throw new InvalidOperationException("Impossible de modifier la commande avec un " + msg + " ID article",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }


    private CommandeClientDto checkEtatCommande(Long idCommande) {

        CommandeClientDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande, Commande deja livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        return commandeClient;
    }

    private Optional<LigneCdeClient> findLigneCommandeClient(Long idLigneCommande) {
        Optional<LigneCdeClient> ligneCommandeClientOptional = Optional.of(ligneCdeClientRepository.findById(idLigneCommande)
                .orElseThrow(
                        () -> new EntityNotFoundException("Ligne commande client introuvable dans la BDD")
                ));

        return ligneCommandeClientOptional;
    }

    private void updateMouvementStock(Long idCommande) {
        List<LigneCdeClient> ligneCdeClients =  ligneCdeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCdeClients.forEach(ligne -> {
            MouvementStckDto stock = MouvementStckDto.builder()
                    .article(ArticleDto.fromEntity(ligne.getArticle()))
                    .dateMouvement(Instant.now())
                    .typeMvtStck(TypeMvtStck.SORTIE)
                    .sourceMvtStock(SourceMvtStock.COMMANDE_CLIENT)
                    .quantite(ligne.getQuantite())
                    .build();

            mouvementStockService.sortieStock(stock);
        });

    }
}
