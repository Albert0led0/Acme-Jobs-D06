
package acme.features.authenticated.investorRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investorRecords.InvestorRecord;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedInvestorRecordShowService implements AbstractShowService<Authenticated, InvestorRecord> {

	@Autowired
	private AuthenticatedInvestorRecordRepository repository;


	@Override
	public boolean authorise(final Request<InvestorRecord> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<InvestorRecord> request, final InvestorRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "investorName", "sector", "investingStatement", "stars");

	}

	@Override
	public InvestorRecord findOne(final Request<InvestorRecord> request) {
		assert request != null;

		InvestorRecord res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneById(id);
		return res;
	}

}
