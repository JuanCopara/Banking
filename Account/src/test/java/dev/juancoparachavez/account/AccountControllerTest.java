package dev.juancoparachavez.account;

import dev.juancoparachavez.account.client.ClientFeign;
import dev.juancoparachavez.account.controller.AccountController;
import dev.juancoparachavez.account.model.Account;
import dev.juancoparachavez.account.model.Transaction;
import dev.juancoparachavez.account.model.dto.ClientDto;
import dev.juancoparachavez.account.repository.AccountRepository;
import dev.juancoparachavez.account.repository.TransactionRepository;
import dev.juancoparachavez.account.service.AccountService;
import dev.juancoparachavez.account.service.AccountServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest extends AbstractSpringContextTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ClientFeign clientFeign;

    @BeforeEach
    void setUp() {
        AccountService accountService = new AccountServiceImpl(accountRepository,clientFeign);
        AccountController accountController = new AccountController(accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void createAccount() throws Exception {
        String request = readJSON("/data/CreateAccountRequest.json");
        ClientDto clientDto = convertTo("/data/ClientDto.json", ClientDto.class);
        Account account = convertTo("/data/AccountEntity.json", Account.class);
        when(clientFeign.getClient(any())).thenReturn(clientDto);
        when(accountRepository.save(any())).thenReturn(account);
        mockMvc.perform(post("/api/accounts")
                .content(request)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void createAccountFail() throws Exception {
        String request = readJSON("/data/CreateAccountRequest.json");
        Account account = convertTo("/data/AccountEntity.json", Account.class);
        when(clientFeign.getClient(any())).thenThrow(FeignException.NotFound.class);
        when(accountRepository.save(any())).thenReturn(account);
        mockMvc.perform(post("/api/accounts")
                        .content(request)
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontró el cliente con el ID " + account.getClientId()));;
    }

    @Test
    void getAccountById() throws Exception {
        Account account = convertTo("/data/AccountEntity.json", Account.class);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        mockMvc.perform(get("/api/accounts/{id}" , account.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void getAccountByIdFail() throws Exception {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isNotFound());
    }

}
