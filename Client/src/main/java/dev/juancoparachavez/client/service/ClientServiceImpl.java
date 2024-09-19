package dev.juancoparachavez.client.service;

import dev.juancoparachavez.client.exception.CustomExceptions;
import dev.juancoparachavez.client.mapper.ClientMapper;
import dev.juancoparachavez.client.model.Client;
import dev.juancoparachavez.client.model.dto.ClientDto;
import dev.juancoparachavez.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;


    @Override
    public List<ClientDto> getAll() {
        List<ClientDto> clientDtos = new ArrayList<>();
        this.clientRepository.findAll().forEach(entity -> clientDtos.add(ClientMapper.MAPPER.toClientDto(entity)));
        return clientDtos;
    }

    @Override
    public ClientDto getById(Long id) {
        return ClientMapper.MAPPER.toClientDto(this.clientRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id )));
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        return ClientMapper.MAPPER.toClientDto(this.clientRepository.save(ClientMapper.MAPPER.toClient(clientDto)));
    }

    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));

        ClientMapper.MAPPER.updateClient(clientDto,client);

        return ClientMapper.MAPPER.toClientDto(this.clientRepository.save(client));
    }

    @Override
    public ClientDto partialUpdate(Long id, ClientDto partialClientDto) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID " + id));

        ClientMapper.MAPPER.partialUpdateClient(partialClientDto,client);
        return  ClientMapper.MAPPER.toClientDto(this.clientRepository.save(client));
    }

    @Override
    public void deleteById(Long id) {
        this.clientRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));
        this.clientRepository.deleteById(id);
    }
}
