
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
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerDutyCreateService implements AbstractCreateService<Employer, Duty> {

	@Autowired
	EmployerDutyRepository	repository;

	@Autowired
	EmployerJobRepository	jobRepository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean res;
		int jobId;
		Job job;
		Employer employer;
		Principal principal;

		principal = request.getPrincipal();
		jobId = request.getModel().getInteger("jobId");
		job = this.jobRepository.findOneJobById(jobId);
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

		model.setAttribute("id", entity.getJob().getId());
	}

	@Override
	public Duty instantiate(final Request<Duty> request) {
		Duty result;

		Integer jobId = request.getModel().getInteger("jobId");

		Job job = this.jobRepository.findOneJobById(jobId);

		result = new Duty();
		result.setJob(job);

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		Job job = this.jobRepository.findOneJobById(request.getModel().getInteger("jobId"));
		entity.setJob(job);

		this.repository.save(entity);
	}

}
