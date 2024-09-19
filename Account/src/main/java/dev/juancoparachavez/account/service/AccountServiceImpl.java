package dev.juancoparachavez.account.service;

import dev.juancoparachavez.account.client.ClientFeign;
import dev.juancoparachavez.account.exception.CustomExceptions;
import dev.juancoparachavez.account.mapper.AccountMapper;
import dev.juancoparachavez.account.model.Account;
import dev.juancoparachavez.account.model.dto.AccountDto;
import dev.juancoparachavez.account.repository.AccountRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final ClientFeign clientFeign;

    @Override
    public List<AccountDto> getAll() {
        List<AccountDto> accounts = new ArrayList<>();
        this.accountRepository.findAll().forEach(entity -> accounts.add(AccountMapper.MAPPER.toAccountDto(entity)));

        return accounts;
    }

    @Override
    public AccountDto getById(Long id) {
        return AccountMapper.MAPPER.toAccountDto(this.accountRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id )));
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        try{
            this.clientFeign.getClient(accountDto.getClientId());
            return AccountMapper.MAPPER.toAccountDto(this.accountRepository.save(AccountMapper.MAPPER.toAccount(accountDto)));
        }
        catch (FeignException.NotFound notFound){
            throw new CustomExceptions.ResourceNotFoundException("No se encontró el cliente con el ID " + accountDto.getClientId());
        }
        catch(Exception e){
            e.printStackTrace();
            throw new CustomExceptions.GenericException(e.getMessage());
        }
    }

    @Override
    public AccountDto update(Long id, AccountDto accountDto) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));
        AccountMapper.MAPPER.updateAccount(accountDto,account);
        return AccountMapper.MAPPER.toAccountDto(this.accountRepository.save(account));
    }

    @Override
    public AccountDto partialUpdate(Long id, AccountDto partialAccountDto) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));
        AccountMapper.MAPPER.partialUpdateaccount(partialAccountDto,account);
        return AccountMapper.MAPPER.toAccountDto(this.accountRepository.save(account));
    }

    @Override
    public void deleteById(Long id) {
        this.accountRepository.findById(id)
                .orElseThrow(()-> new CustomExceptions.ResourceNotFoundException("No se encontraron registros con el ID "+ id ));
        this.accountRepository.deleteById(id);
    }
}
