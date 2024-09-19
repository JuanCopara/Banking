package dev.juancoparachavez.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity{

    private static final long serialVersionUID = 184610L;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private Account account;

}
