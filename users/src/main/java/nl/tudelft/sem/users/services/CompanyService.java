package nl.tudelft.sem.users.services;

import nl.tudelft.sem.users.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyService {

    private final transient CompanyRepository companyRepository;

    /**
     * Constructor of CompanyRepository - It instantiates a new CompanyService object.
     *
     * @param companyRepository repository injected with data from the database
     */

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
}
