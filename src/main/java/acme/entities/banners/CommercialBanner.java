
package acme.entities.banners;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommercialBanner extends Banner {

	//Serialisation identifier -------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes ---------------------------------------------------------

	@NotBlank
	@CreditCardNumber
	private String				creditCard;

	@NotNull
	@Max(999)
	private Integer				cvv;

	@NotNull
	private Date				expirationDate;

}
