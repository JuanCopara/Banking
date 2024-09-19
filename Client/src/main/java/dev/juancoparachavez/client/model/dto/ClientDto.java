package dev.juancoparachavez.client.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientDto implements Serializable {

    private static final long serialVersionUID = 14554L;
    private Long id;
    private String dni;
    private String name;
    private String password;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private Boolean isActive;
}
