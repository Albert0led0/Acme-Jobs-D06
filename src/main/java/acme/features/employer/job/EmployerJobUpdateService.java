
package acme.features.employer.job;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerJobUpdateService implements AbstractUpdateService<Employer, Job> {

	@Autowired
	private EmployerJobRepository repository;


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

		Job pastEntity = this.repository.findOneJobById(entity.getId());
		if (!pastEntity.getReferenceNumber().equals(entity.getReferenceNumber())) {
			errors.state(request, !this.repository.checkUniqueReference(entity.getReferenceNumber()), "referenceNumber", "employer.job.error.unique-reference");
		}

		Date now;
		now = new Date(System.currentTimeMillis());
		if (entity.getDeadline() != null) {
			errors.state(request, entity.getDeadline().after(now), "deadline", "employer.job.error.past-deadline");
		}

		errors.state(request, !request.getModel().getString("status").equals("published"), "status", "employer.job.error.draft");

		if (!entity.isDraft()) {
			//duty
			if (!this.repository.findJobDuties(entity.getId()).isEmpty()) {
				errors.state(request, this.repository.timePercentageSum(entity.getId()), "description", "employer.job.error.percentage");
			}

			//spam
			Boolean isSpam = false;
			String spamWordsAux = "";
			Double wordCount;
			Double spamCount = 0d;
			Double percentage;
			Double threshold;
			Boolean nullLink = entity.getLink() == null;

			Collection<String> spamWords1 = this.repository.retrieveSpamWords();
			List<String> spamWords = spamWords1.stream().collect(Collectors.toList());

			String spam1 = spamWords.get(0);
			String spam2 = spamWords.get(1);

			spamWordsAux += spam1 + "," + spam2;

			spamWords.clear();
			spamWords.addAll(Arrays.asList(spamWordsAux.split(",")));
			spamWords = spamWords.stream().map(String::trim).collect(Collectors.toList());

			wordCount = 1.0 * entity.getReferenceNumber().length() + entity.getTitle().length() + entity.getDescription().length();
			if (!nullLink) {
				wordCount += entity.getLink().length();
			}

			for (String sw : spamWords) {
				Integer loopAux = 0;
				loopAux += entity.getReferenceNumber().toLowerCase().split(sw, -1).length - 1;
				loopAux += entity.getTitle().toLowerCase().split(sw, -1).length - 1;
				if (!nullLink) {
					loopAux += entity.getLink().toLowerCase().split(sw, -1).length - 1;
				}
				loopAux += entity.getDescription().toLowerCase().split(sw, -1).length - 1;
				spamCount += loopAux * sw.length();
			}

			percentage = spamCount / wordCount;

			String language = request.getLocale().getLanguage();
			threshold = this.repository.retrieveThreshold(language);

			isSpam = percentage >= threshold;
			if (isSpam) {
				errors.state(request, false, "description", "employer.job.error.spam");
			}

		}

	}

	@Override
	public void update(final Request<Job> request, final Job entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
