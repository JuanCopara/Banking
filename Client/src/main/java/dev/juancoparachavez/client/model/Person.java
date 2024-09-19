package dev.juancoparachavez.client.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class Person extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1515320L;
    private String name;
    private String dni;
    private String gender;
    private Integer age;
    private String address;
    private String phone;

}
