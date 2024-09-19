package dev.juancoparachavez.account.repository;

import java.util.Date;
import java.util.List;

import dev.juancoparachavez.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("FROM Transaction t WHERE t.account.clientId in ?1 and t.date between ?2 and ?3")
    List<Transaction> getAllByClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
                                                       Date dateTransactionEnd);

    @Query("FROM Transaction t WHERE t.account.id = ?1")
    List<Transaction> getByAccountId(Long clientId);
}
