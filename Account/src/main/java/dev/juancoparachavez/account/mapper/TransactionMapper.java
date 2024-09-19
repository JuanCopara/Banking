package dev.juancoparachavez.account.mapper;

import dev.juancoparachavez.account.model.Account;
import dev.juancoparachavez.account.model.Transaction;
import dev.juancoparachavez.account.model.dto.BankStatementDto;
import dev.juancoparachavez.account.model.dto.ClientDto;
import dev.juancoparachavez.account.model.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "accountId", source = "account.id")
    TransactionDto toTransactionDto(Transaction transaction);

    default Transaction toTransaction(TransactionDto transactionDto, Account account){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType());
        transaction.setDate(transactionDto.getDate());
        transaction.setBalance(transactionDto.getBalance());
        transaction.setAccount(account);
        return transaction;
    }

    @Mapping(target = "id",ignore = true)
    Transaction updateTransaction(TransactionDto transactionDto, @MappingTarget Transaction transaction);

    default Transaction partialUpdate(TransactionDto transactionDto, Transaction transaction){
        return transaction;
    }

    default List<BankStatementDto> toBankStatementsDto(List<Transaction> transactions, ClientDto clientDto){
        List<BankStatementDto> bankStatementDtos = new ArrayList<>();
        transactions.forEach(transaction -> {
            bankStatementDtos.add(toBankStatementDto(transaction, clientDto));
        });
        return bankStatementDtos;
    }

    default BankStatementDto toBankStatementDto(Transaction transaction, ClientDto clientDto){
        BankStatementDto bankStatementDto = new BankStatementDto();
        bankStatementDto.setClient(clientDto.getName());
        bankStatementDto.setDate(transaction.getDate());
        bankStatementDto.setBalance(transaction.getBalance());
        bankStatementDto.setAmount(transaction.getAmount());
        bankStatementDto.setAccountType(transaction.getAccount().getType());
        bankStatementDto.setAccountNumber(transaction.getAccount().getNumber());
        bankStatementDto.setInitialAmount(transaction.getAccount().getInitialAmount());
        bankStatementDto.setIsActive(transaction.getAccount().getIsActive());
        bankStatementDto.setTransactionType(transaction.getType());

        return bankStatementDto;
    }


}
