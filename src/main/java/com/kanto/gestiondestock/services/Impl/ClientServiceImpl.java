package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.ClientDto;
import com.kanto.gestiondestock.entity.Client;
import com.kanto.gestiondestock.entity.CommandeClient;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.exception.InvalidOperationException;
import com.kanto.gestiondestock.repository.ClientRepository;
import com.kanto.gestiondestock.repository.CommandeClientRepository;
import com.kanto.gestiondestock.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommandeClientRepository commandeClientRepository;

    @Override
    public ClientDto save(ClientDto clientDto) {

        return ClientDto.fromEntity(
                clientRepository.save(ClientDto.toEntity(clientDto))
        );

    }

    @Override
    public ClientDto findById(Long id) {

        if (id == null) {
            log.error("Client ID est null");
            return null;
        }

        Optional<Client> client = Optional.of(clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Aucun client avec ID : " + id + " n'a ete trouver")));

        return ClientDto.fromEntity(client.get());
    }

    @Override
    public List<ClientDto> findAll() {

        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            log.error("Client ID est null pour la delete");
            return;
        }

        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()) {
            throw  new InvalidOperationException("Impossible de supprimer un client deja assigner dans une commande client",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }

        clientRepository.deleteById(id);

    }
}
