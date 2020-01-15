
package acme.entities.creditCards;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CreditCard extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@CreditCardNumber
	private String				number;

	@NotBlank
	@Pattern(regexp = "^[0-9]{3}$")
	private String				cvv;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				expirationDate;

}
