
package acme.features.employer.duty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EmployerDutyShowService implements AbstractShowService<Employer, Duty> {

	//internal state -------------------------------------------------------------

	@Autowired
	EmployerDutyRepository repository;


	// AbstractListService<Employer, Job> interface ------------------------------

	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean res;
		int dutyId;
		Duty duty;
		Job job;
		Employer employer;
		Principal principal;

		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findOneDutyById(dutyId);
		job = duty.getJob();
		employer = job.getEmployer();
		principal = request.getPrincipal();
		res = employer.getUserAccount().getId() == principal.getAccountId();

		return res;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "timePercentage");

		model.setAttribute("draft", entity.getJob().isDraft());
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

}
