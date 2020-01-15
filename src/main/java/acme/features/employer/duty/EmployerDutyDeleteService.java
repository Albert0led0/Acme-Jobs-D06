
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.features.employer.job.EmployerJobRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class EmployerDutyDeleteService implements AbstractDeleteService<Employer, Duty> {

	@Autowired
	private EmployerDutyRepository	repository;

	@Autowired
	private EmployerJobRepository	jobRepository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean res;
		int dutyId;
		Job job;
		Employer employer;
		Principal principal;

		principal = request.getPrincipal();
		dutyId = request.getModel().getInteger("id");
		job = this.repository.findJobByDutyId(dutyId);
		employer = job.getEmployer();

		res = employer.getUserAccount().getId() == principal.getAccountId() && job.isDraft();

		return res;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "timePercentage");

	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneDutyById(id);

		return res;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

}
