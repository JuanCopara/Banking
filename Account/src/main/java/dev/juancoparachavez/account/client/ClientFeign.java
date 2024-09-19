package dev.juancoparachavez.account.client;

import dev.juancoparachavez.account.model.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="clientFeign", url="${client.url}")
public interface ClientFeign {

    @GetMapping("/api/clients/{id}")
    ClientDto getClient(@PathVariable("id") Long id);
}
