package dev.juancoparachavez.account.controller;

import dev.juancoparachavez.account.exception.CustomExceptions;
import dev.juancoparachavez.account.exception.ErrorResponse;
import dev.juancoparachavez.account.model.dto.BankStatementDto;
import dev.juancoparachavez.account.model.dto.TransactionDto;
import dev.juancoparachavez.account.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getAll(){
        return ResponseEntity.ok(this.transactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> get(@PathVariable Long id){
        return ResponseEntity.ok( this.transactionService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<TransactionDto> create(@RequestBody @Valid TransactionDto transactionDto){
        return ResponseEntity.ok( this.transactionService.create(transactionDto));
    }

    @GetMapping("/clients/{clientId}/report")
    public ResponseEntity<List<BankStatementDto>> report(@PathVariable Long clientId,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {
        return ResponseEntity.ok(
                this.transactionService.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> update(@PathVariable Long id,
                                                 @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(this.transactionService.updateById(id, transactionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.transactionService.deleteById(id);
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
