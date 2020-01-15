
package acme.features.administrator.commercialBanner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorCommercialBannerUpdateService implements AbstractUpdateService<Administrator, CommercialBanner> {

	@Autowired
	AdministratorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		//		assert request != null;
		//		assert entity != null;
		//		assert errors != null;
		//
		//		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "pictureURL", "slogan", "targetURL", "creditCard", "cvv", "expirationDate");

	}

	@Override
	public CommercialBanner findOne(final Request<CommercialBanner> request) {
		assert request != null;

		CommercialBanner result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Boolean validExpirationDate = true;
		Date now = new Date(System.currentTimeMillis());

		try {
			assert entity.getExpirationDate() != null;
		} catch (AssertionError e1) {
			validExpirationDate = false;
			errors.state(request, false, "expirationDate", "administrator.commercial-banner.form.error.timestamp");
		}

		if (validExpirationDate) {
			errors.state(request, entity.getExpirationDate().after(now), "expirationDate", "administrator.commercial-banner.form.error.past-deadline");
		}

	}

	@Override
	public void update(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}