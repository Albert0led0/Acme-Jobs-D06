
package acme.features.sponsor.creditCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.creditCards.CreditCard;
import acme.entities.roles.Sponsor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class SponsorCreditCardShowService implements AbstractShowService<Sponsor, CreditCard> {

	@Autowired
	private SponsorCreditCardRepository repository;


	@Override
	public boolean authorise(final Request<CreditCard> request) {
		assert request != null;

		boolean res;
		int creditCardId;
		CreditCard creditCard;
		Sponsor sponsor;
		Principal principal;

		creditCardId = request.getModel().getInteger("id");
		creditCard = this.repository.findOneCreditCardById(creditCardId);
		sponsor = this.repository.findSponsorByCreditCard(creditCard);
		principal = request.getPrincipal();

		res = sponsor.getUserAccount().getId() == principal.getAccountId();
		return res;
	}

	@Override
	public void unbind(final Request<CreditCard> request, final CreditCard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "number", "cvv", "expirationDate");

	}

	@Override
	public CreditCard findOne(final Request<CreditCard> request) {
		assert request != null;
		CreditCard res;

		int id = request.getModel().getInteger("id");
		res = this.repository.findOneCreditCardById(id);

		return res;
	}

}
