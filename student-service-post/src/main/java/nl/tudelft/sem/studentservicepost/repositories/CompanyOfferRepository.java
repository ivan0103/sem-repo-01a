package nl.tudelft.sem.studentservicepost.repositories;

import java.util.Collection;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyOfferRepository extends JpaRepository<CompanyOffer, Long> {

    CompanyOffer getById(Long id);

    Collection<CompanyOffer> getAllByCompanyId(String companyId);

}
