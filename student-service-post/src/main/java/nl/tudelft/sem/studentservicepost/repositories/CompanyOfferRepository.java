package nl.tudelft.sem.studentservicepost.repositories;

import java.util.Collection;
import java.util.Set;
import nl.tudelft.sem.studentservicepost.entities.CompanyOffer;
import nl.tudelft.sem.studentservicepost.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyOfferRepository extends JpaRepository<CompanyOffer, Long> {

    CompanyOffer getById(Long id);

    Collection<CompanyOffer> getAllByCompanyId(String companyId);

    Set<CompanyOffer> getAllByPost(Post post);

}
