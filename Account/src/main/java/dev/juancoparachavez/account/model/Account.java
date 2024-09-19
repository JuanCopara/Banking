package dev.juancoparachavez.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account extends BaseEntity {

    private static final long serialVersionUID = 151651610L;

    private String number;
    private String type;
    private BigDecimal initialAmount;
    private Boolean isActive;

    @Column(name = "client_Id")
    private Long clientId;

}
