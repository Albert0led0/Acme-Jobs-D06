
package acme.features.administrator.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	private AdministratorDashboardRepository repository;


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Integer numberOfAnnouncements = this.repository.numberOfAnnouncements();
		Integer numberOfCompanyRecords = this.repository.numberOfCompanyRecords();
		Integer numberOfInvestorRecords = this.repository.numberOfInvestorRecords();

		List<Object[]> companiesAndSectors = new ArrayList<Object[]>(this.repository.companiesBySector());
		List<String> cSectors = new ArrayList<String>();
		List<Long> companiesBySector = new ArrayList<Long>();
		for (Object[] cs : companiesAndSectors) {
			cSectors.add((String) cs[0]);
			companiesBySector.add((Long) cs[1]);
		}

		List<Object[]> investorsAndSectors = new ArrayList<Object[]>(this.repository.investorsBySector());
		List<String> iSectors = new ArrayList<String>();
		List<Long> investorsBySector = new ArrayList<Long>();
		for (Object[] is : investorsAndSectors) {
			iSectors.add((String) is[0]);
			investorsBySector.add((Long) is[1]);
		}

		entity.setNumberOfAnnouncements(numberOfAnnouncements);
		entity.setNumberOfCompanyRecords(numberOfCompanyRecords);
		entity.setNumberOfInvestorRecords(numberOfInvestorRecords);

		entity.setCSectors(cSectors);
		entity.setCompaniesBySector(companiesBySector);

		entity.setISectors(iSectors);
		entity.setInvestorsBySector(investorsBySector);

		request.unbind(entity, model, "numberOfAnnouncements", "numberOfCompanyRecords", "numberOfInvestorRecords", "cSectors", "companiesBySector", "iSectors", "investorsBySector");

	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		Dashboard d = new Dashboard();
		return d;
	}

}
