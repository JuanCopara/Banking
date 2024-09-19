package dev.juancoparachavez.account.service;

import dev.juancoparachavez.account.model.dto.BankStatementDto;
import dev.juancoparachavez.account.model.dto.TransactionDto;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    List<TransactionDto> getAll();
    TransactionDto getById(Long id);
    TransactionDto create(TransactionDto transactionDto);
    List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart, Date dateTransactionEnd);
    TransactionDto getLastByAccountId(Long accountId);
    TransactionDto updateById(Long id, TransactionDto transactionDto);
    void deleteById(Long id);
}
