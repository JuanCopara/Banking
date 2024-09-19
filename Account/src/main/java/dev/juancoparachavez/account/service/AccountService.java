package dev.juancoparachavez.account.service;

import dev.juancoparachavez.account.model.dto.AccountDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAll();
    AccountDto getById(Long id);
    AccountDto create(AccountDto accountDto);
    AccountDto update(Long id, AccountDto accountDto);
    AccountDto partialUpdate(Long id, AccountDto partialAccountDto);
    void deleteById(Long id);
}
