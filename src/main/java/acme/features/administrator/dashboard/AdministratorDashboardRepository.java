
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Announcement a")
	Integer numberOfAnnouncements();

	@Query("select count(cr) from CompanyRecord cr")
	Integer numberOfCompanyRecords();

	@Query("select count(ir) from InvestorRecord ir")
	Integer numberOfInvestorRecords();

	@Query("select cr.sector, count(cr) from CompanyRecord cr group by cr.sector")
	Collection<Object[]> companiesBySector();

	@Query("select ir.sector, count(ir) from InvestorRecord ir group by ir.sector")
	Collection<Object[]> investorsBySector();

}
