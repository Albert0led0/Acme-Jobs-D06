
package acme.features.administrator.companyRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.companyRecords.CompanyRecord;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorCompanyRecordShowService implements AbstractShowService<Administrator, CompanyRecord> {

	@Autowired
	AdministratorCompanyRecordRepository repository;


	@Override
	public boolean authorise(final Request<CompanyRecord> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<CompanyRecord> request, final CompanyRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "company", "sector", "ceoName", "description", "webSite", "phoneNumber", "email", "stars", "incorporated");

	}

	@Override
	public CompanyRecord findOne(final Request<CompanyRecord> request) {
		assert request != null;

		CompanyRecord res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneCompanyRecordById(id);
		return res;
	}

}
