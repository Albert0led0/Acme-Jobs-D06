
package acme.features.employer.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerJobCreateService implements AbstractCreateService<Employer, Job> {

	@Autowired
	private EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "draft");

	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "referenceNumber", "title", "deadline", "salary", "link", "description");

	}

	@Override
	public Job instantiate(final Request<Job> request) {
		Job job;
		Employer employer;
		Principal principal;

		principal = request.getPrincipal();
		int principalAccId = principal.getAccountId();

		employer = this.repository.findEmployerById(principalAccId);

		job = new Job();
		job.setEmployer(employer);

		return job;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date now;

		now = new Date(System.currentTimeMillis());
		if (entity.getDeadline() != null) {
			errors.state(request, entity.getDeadline().after(now), "deadline", "employer.job.error.past-deadline");
		}
		errors.state(request, !this.repository.checkUniqueReference(entity.getReferenceNumber()), "referenceNumber", "employer.job.error.unique-reference");

	}

	@Override
	public void create(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		boolean draft = true;
		entity.setDraft(draft);

		this.repository.save(entity);

	}

}
