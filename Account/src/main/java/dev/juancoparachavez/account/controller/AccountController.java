package dev.juancoparachavez.account.controller;

import dev.juancoparachavez.account.exception.CustomExceptions;
import dev.juancoparachavez.account.exception.ErrorResponse;
import dev.juancoparachavez.account.model.dto.AccountDto;
import dev.juancoparachavez.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<List<AccountDto>> getAll() {
        return ResponseEntity.ok(this.accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> get(@PathVariable Long id) {
        return  ResponseEntity.ok(this.accountService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(this.accountService.create(accountDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(this.accountService.update(id, accountDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
                                                    @RequestBody AccountDto partialAccountDto) {
        return ResponseEntity.ok(this.accountService.partialUpdate(id,partialAccountDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.accountService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CustomExceptions.ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(CustomExceptions.ResourceNotFoundException ex) {
        return new ErrorResponse("Recurso no encontrado", ex.getMessage());
    }

    @ExceptionHandler(CustomExceptions.InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(CustomExceptions.InvalidRequestException ex) {
        return new ErrorResponse("Solicitud inválida", ex.getMessage());
    }

}
