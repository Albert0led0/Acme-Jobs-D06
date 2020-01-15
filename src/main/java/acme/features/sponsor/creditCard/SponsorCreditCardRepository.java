
package acme.features.sponsor.creditCard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.creditCards.CreditCard;
import acme.entities.roles.Sponsor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SponsorCreditCardRepository extends AbstractRepository {

	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findOneSponsorByUserAccountId(int id);

	@Query("select c from CreditCard c where c.id=?1")
	CreditCard findOneCreditCardById(int id);

	@Query("select s from Sponsor s where s.creditCard = ?1")
	Sponsor findSponsorByCreditCard(CreditCard creditCard);

}
