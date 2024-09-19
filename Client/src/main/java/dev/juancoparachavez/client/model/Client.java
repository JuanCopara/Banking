package dev.juancoparachavez.client.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client extends Person{

    private static final long serialVersionUID = 15150021L;
    private String password;
    private Boolean isActive;
}
