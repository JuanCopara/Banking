package dev.juancoparachavez.account.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankStatementDto implements Serializable {

	private static final long serialVersionUID = 1651551L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	private String client;
	private String accountNumber;
	private String accountType;
	private BigDecimal initialAmount;
    private Boolean isActive;
	private String transactionType;
	private BigDecimal amount;
	private BigDecimal balance;
}
