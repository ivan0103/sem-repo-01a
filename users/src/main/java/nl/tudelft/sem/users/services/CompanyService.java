package nl.tudelft.sem.users.services;

import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyService implements UserService<Company> {

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

    /**
     * Getter for all the companies in the database.
     *
     * @return a list of companies contained in the database
     */

    @Override
    public List<Company> getUsers() {
        return companyRepository.findAll();
    }

    /**
     * Retrieves only one company based on the id.
     *
     * @param netID - the id of the company to be retrieved
     * @return the company
     */

    @Override
    public Company getOneUser(String netID) {
        if (companyRepository.findById(netID).isEmpty()) {
            //throw new IllegalStateException("Company nonexistent :/");
            return null;
        }

        return companyRepository.findById(netID).get();
    }
}
