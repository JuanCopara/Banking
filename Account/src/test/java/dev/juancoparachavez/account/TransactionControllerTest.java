package dev.juancoparachavez.account;

import dev.juancoparachavez.account.client.ClientFeign;
import dev.juancoparachavez.account.controller.TransactionController;
import dev.juancoparachavez.account.model.Account;
import dev.juancoparachavez.account.model.Transaction;
import dev.juancoparachavez.account.repository.AccountRepository;
import dev.juancoparachavez.account.repository.TransactionRepository;
import dev.juancoparachavez.account.service.TransactionService;
import dev.juancoparachavez.account.service.TransactionServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest extends AbstractSpringContextTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ClientFeign clientFeign;

    @BeforeEach
    void setUp(){
        TransactionService transactionService = new TransactionServiceImpl(accountRepository,transactionRepository,clientFeign);
        TransactionController transactionController = new TransactionController(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void createTransaction() throws Exception {
        String request = readJSON("/data/CreateTransactionRequest.json");
        Account account = convertTo("/data/AccountEntity.json", Account.class);
        Transaction transaction = convertTo("/data/TransactionEntity.json", Transaction.class);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);
        when(transactionRepository.save(any())).thenReturn(transaction);
        mockMvc.perform(post("/api/transactions")
                        .content(request)
                        .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void createTransactionWithInvalidAccount() throws Exception {
        String request = readJSON("/data/CreateTransactionRequest.json");
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        mockMvc.perform(post("/api/transactions")
                        .content(request)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTransactionWithInvalidAmmount() throws Exception {
        String request = readJSON("/data/CreateTransactionRequest.json");
        Account account = convertTo("/data/AccountWithNoFunds.json", Account.class);
        Transaction transaction = convertTo("/data/TransactionEntity.json", Transaction.class);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);
        when(transactionRepository.save(any())).thenReturn(transaction);
        mockMvc.perform(post("/api/transactions")
                        .content(request)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
