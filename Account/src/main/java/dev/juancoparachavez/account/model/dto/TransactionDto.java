package dev.juancoparachavez.account.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.juancoparachavez.account.validators.BigDecimalNotZero;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 165162L;
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
	private String type;
	@NotNull(message = "No puede ser nulo")
	@BigDecimalNotZero(message = "El monto no puede ser 0")
	private BigDecimal amount;
	private BigDecimal balance;
	private Long accountId;
}
