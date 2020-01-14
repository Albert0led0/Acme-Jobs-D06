
package acme.features.worker.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Worker;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class WorkerApplicationCreateService implements AbstractCreateService<Worker, Application> {

	@Autowired
	WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment", "status");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "referenceNumber", "statement", "skills", "qualifications");
		model.setAttribute("id", entity.getJob().getId());
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		Application result;
		Worker worker;
		Principal principal;

		Job job = this.repository.findOneJobById(request.getModel().getInteger("jobId"));
		principal = request.getPrincipal();
		int principalAccId = principal.getAccountId();

		worker = this.repository.findOneWorkerByUserAccountId(principalAccId);

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);

		result = new Application();
		result.setMoment(moment);
		result.setWorker(worker);
		result.setJob(job);

		String status = "pending";
		result.setStatus(status);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		errors.state(request, !this.repository.checkUniqueReference(entity.getReferenceNumber()), "referenceNumber", "worker.application.error.unique-reference");

	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		Job job = this.repository.findOneJobById(request.getModel().getInteger("jobId"));
		entity.setJob(job);

		this.repository.save(entity);
	}

}
