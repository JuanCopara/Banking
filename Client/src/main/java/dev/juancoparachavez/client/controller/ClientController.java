package dev.juancoparachavez.client.controller;

import dev.juancoparachavez.client.model.dto.ClientDto;
import dev.juancoparachavez.client.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<List<ClientDto>> getAll(){
        return ResponseEntity.ok( this.clientService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> get(@PathVariable Long id){
        return ResponseEntity.ok( this.clientService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto){
        return ResponseEntity.ok( this.clientService.create(clientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto){
        return ResponseEntity.ok( this.clientService.update(id, clientDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, @RequestBody ClientDto partialClientDto){
        return ResponseEntity.ok( this.clientService.partialUpdate(id, partialClientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}


