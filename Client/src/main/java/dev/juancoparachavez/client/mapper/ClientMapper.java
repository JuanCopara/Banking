package dev.juancoparachavez.client.mapper;

import dev.juancoparachavez.client.model.Client;
import dev.juancoparachavez.client.model.dto.ClientDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientMapper MAPPER = Mappers.getMapper(ClientMapper.class);

    ClientDto toClientDto(Client client);

    Client toClient(ClientDto clientDto);

    @Mapping(target = "id", ignore = true)
    Client updateClient(ClientDto clientDto, @MappingTarget Client client);

    @InheritConfiguration(name = "updateClient")
    Client updateFromDto(ClientDto clientDto, @MappingTarget Client client);

    default Client partialUpdateClient(ClientDto clientDto, Client client) {
        client.setIsActive(clientDto.getIsActive());
        return client;
    }
}
