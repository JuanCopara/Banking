package dev.juancoparachavez.account.mapper;

import dev.juancoparachavez.account.model.Account;
import dev.juancoparachavez.account.model.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);
    @Mapping(target = "id", ignore = true)
    Account updateAccount(AccountDto accountDto,@MappingTarget Account account);

    default Account partialUpdateaccount(AccountDto accountDto, Account account) {
        account.setIsActive(accountDto.getIsActive());
        return account;
    }

}
