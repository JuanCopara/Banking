package dev.juancoparachavez.account.repository;

import dev.juancoparachavez.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    @Query("FROM Account ac WHERE ac.clientId = ?1")
    List<Account> findAllByClientId(Long ClientId);

}
