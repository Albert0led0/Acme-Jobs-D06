
package acme.features.employer.job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id = ?1")
	Job findOneJobById(int id);

	@Query("select j from Job j where j.employer.id = ?1")
	Collection<Job> findManyByEmployerId(int employerId);

	@Query("select count(a)>0 from Application a where a.job.id= ?1")
	Boolean jobHasApplications(int jobId);

	@Query("select e from Employer e where e.userAccount.id = ?1")
	Employer findEmployerById(int principalAccId);

	@Query("select count(j)>0 from Job j where j.referenceNumber = ?1")
	Boolean checkUniqueReference(String referenceNumber);

	@Query("select sum(d.timePercentage)<=1.0 from Duty d where d.job.id = ?1")
	Boolean timePercentageSum(int jobId);

	@Query("select d from Duty d where d.job.id = ?1")
	Collection<Duty> findJobDuties(int jobId);

	@Query("select c.spamwords from Configuration c")
	Collection<String> retrieveSpamWords();

	@Query("select c.spamThreshold from Configuration c where c.language = ?1")
	Double retrieveThreshold(String language);

}
