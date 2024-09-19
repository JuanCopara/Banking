package dev.juancoparachavez.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {

	private static final long serialVersionUID = 15102L;
	private Long id;
	private String number;
	private String type;
	private BigDecimal initialAmount;
	private Boolean isActive;
	private Long clientId;

}
