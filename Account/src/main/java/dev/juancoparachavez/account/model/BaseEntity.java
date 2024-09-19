package dev.juancoparachavez.account.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 12455151L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
