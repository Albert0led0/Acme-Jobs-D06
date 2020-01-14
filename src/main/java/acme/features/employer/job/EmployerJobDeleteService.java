
package acme.features.employer.job;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.features.employer.duty.EmployerDutyRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class EmployerJobDeleteService implements AbstractDeleteService<Employer, Job> {

	@Autowired
	private EmployerJobRepository	repository;

	@Autowired
	private EmployerDutyRepository	dutyRepository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		boolean res;
		int jobId;
		Job job;
		Employer employer;
		Principal principal;

		principal = request.getPrincipal();
		jobId = request.getModel().getInteger("id");
		job = this.repository.findOneJobById(jobId);
		employer = job.getEmployer();

		res = employer.getUserAccount().getId() == principal.getAccountId();
		return res;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "referenceNumber", "title", "deadline", "salary", "link", "description", "status", "draft");

	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		int jobId;
		Job res;

		jobId = request.getModel().getInteger("id");
		res = this.repository.findOneJobById(jobId);

		return res;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		int jobId;
		jobId = request.getModel().getInteger("id");

		errors.state(request, !this.repository.jobHasApplications(jobId), "description", "employer.job.error.no-apps");

	}

	@Override
	public void delete(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		int jobId;

		jobId = request.getModel().getInteger("id");
		Collection<Duty> duties = this.dutyRepository.findJobDuties(jobId);
		this.dutyRepository.deleteAll(duties);
		this.repository.delete(entity);

	}

}
