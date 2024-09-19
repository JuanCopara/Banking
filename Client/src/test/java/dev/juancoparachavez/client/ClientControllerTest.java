package dev.juancoparachavez.client;

import dev.juancoparachavez.client.controller.ClientController;
import dev.juancoparachavez.client.model.Client;
import dev.juancoparachavez.client.repository.ClientRepository;
import dev.juancoparachavez.client.service.ClientService;
import dev.juancoparachavez.client.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest extends AbstractSpringContextTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp(){
        ClientService clientService = new ClientServiceImpl(clientRepository);
        ClientController clientController = new ClientController(clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void createClientTest() throws Exception {
        String request = readJSON("/data/CreateClientRequest.json");
        Client client =  convertTo("/data/ClientEntity.json", Client.class);
        when(clientRepository.save(any())).thenReturn(client);
        mockMvc.perform(post("/api/clients")
                .content(request)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllClients() throws Exception {

        List<Client> client =  convertToList("/data/MultipleClientsEntity.json", Client.class);
        when(clientRepository.findAll()).thenReturn(client);
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());

    }

    @Test
    void getClientByIdTest() throws Exception {
        Client client =  convertTo("/data/ClientEntity.json", Client.class);
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        mockMvc.perform(get("/api/clients/{id}", client.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void updateClientTest() throws Exception {
        String request = readJSON("/data/UpdateClientRequest.json");
        Client client =  convertTo("/data/ClientEntity.json", Client.class);
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        mockMvc.perform(put("/api/clients/{id}", client.getId())
                        .content(request)
                        .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteClientTest() throws Exception {
        Client client =  convertTo("/data/ClientEntity.json", Client.class);
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        mockMvc.perform(delete("/api/clients/{id}",client.getId()))
                .andExpect(status().isOk());
    }

}
