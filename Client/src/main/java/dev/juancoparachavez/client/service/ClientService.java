package dev.juancoparachavez.client.service;

import dev.juancoparachavez.client.model.dto.ClientDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getAll();
    ClientDto getById(Long id);
    ClientDto create(ClientDto clientDto);
    ClientDto update(Long id,ClientDto clientDto);
    ClientDto partialUpdate(Long id, ClientDto partialClientDto);
    void deleteById(Long id);
}
