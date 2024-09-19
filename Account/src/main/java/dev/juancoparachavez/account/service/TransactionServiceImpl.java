package dev.juancoparachavez.account.service;

import dev.juancoparachavez.account.client.ClientFeign;
import dev.juancoparachavez.account.exception.CustomExceptions;
import dev.juancoparachavez.account.mapper.TransactionMapper;
import dev.juancoparachavez.account.model.Account;
import dev.juancoparachavez.account.model.Transaction;
import dev.juancoparachavez.account.model.dto.BankStatementDto;
import dev.juancoparachavez.account.model.dto.ClientDto;
import dev.juancoparachavez.account.model.dto.TransactionDto;
import dev.juancoparachavez.account.repository.AccountRepository;
import dev.juancoparachavez.account.repository.TransactionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final AccountRepository  accountRepository;
    private final TransactionRepository transactionRepository;
    private final ClientFeign clientFeign;


    @Override
    public List<TransactionDto> getAll() {
        List<TransactionDto> transactionDtos = new ArrayList<>();

        this.transactionRepository.findAll().forEach(entity -> transactionDtos.add(TransactionMapper.MAPPER.toTransactionDto(entity)));

        return transactionDtos;
    }

    @Override
    public TransactionDto getById(Long id) {
        return TransactionMapper.MAPPER.toTransactionDto(this.transactionRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID " + id)));
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Account account = this.accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(()->new CustomExceptions.ResourceNotFoundException("Cuenta no encontrada"));

        if(Boolean.FALSE.equals(account.getIsActive()))
            throw new CustomExceptions.InvalidRequestException("Cuenta no activa");
        BigDecimal calculatedBalance = calculateBalance(transactionDto,account);
        transactionDto.setBalance(calculatedBalance);
        account.setInitialAmount(calculatedBalance);
        this.accountRepository.save(account);
        return TransactionMapper.MAPPER.toTransactionDto(this.transactionRepository.save(TransactionMapper.MAPPER.toTransaction(transactionDto, account)));
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart, Date dateTransactionEnd) {

        try{
            CompletableFuture<ClientDto> clientFuture = getClientAsync(clientId);
            List<Transaction> transactions = transactionRepository.getAllByClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);
            ClientDto clientDto = clientFuture.get(5, TimeUnit.SECONDS);

            return TransactionMapper.MAPPER.toBankStatementsDto(transactions, clientDto);

        } catch (FeignException.NotFound notFound) {
            throw new CustomExceptions.ResourceNotFoundException("No se encontró el cliente con el ID " + clientId);
        } catch (Exception e) {
            throw new CustomExceptions.GenericException(e.getMessage());
        }
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        return null;
    }

    @Override
    public TransactionDto updateById(Long id, TransactionDto transactionDto) {
        Transaction transaction = this.transactionRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));
        TransactionMapper.MAPPER.updateTransaction(transactionDto,transaction);
        return TransactionMapper.MAPPER.toTransactionDto(transaction);
    }

    @Override
    public void deleteById(Long id) {
        this.transactionRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));
        this.transactionRepository.deleteById(id);
    }

    @Async
    public CompletableFuture<ClientDto> getClientAsync(Long clientId) {
        try {
            ClientDto clientDto = clientFeign.getClient(clientId);
            return CompletableFuture.completedFuture(clientDto);
        } catch (FeignException.NotFound notFound) {
            throw new CustomExceptions.ResourceNotFoundException("No se encontró el cliente con el ID " + clientId);
        }
    }

    private BigDecimal calculateBalance(TransactionDto transactionDto,Account account){

        BigDecimal updatedAmount = account.getInitialAmount().add(transactionDto.getAmount());
        if (updatedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomExceptions.InvalidRequestException("Saldo no disponible");
        }
        return updatedAmount;
    }


}
