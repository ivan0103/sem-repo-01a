package nl.tudelft.sem.contracts.services;

import nl.tudelft.sem.contracts.entities.Contract;
import nl.tudelft.sem.contracts.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ContractService {
    private final transient ContractRepository contractRepository;

    /**
     * Auto-wires the contractRepository to this class to use its methods.
     *
     * @param contractRepository - repository for contracts.
     */
    @Autowired
    public ContractService(@Qualifier("ContractRepository") ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Uses the contractRepository to store an instance of a contract and return this contract.
     *
     * @param contract - the contract to store.
     * @return - The stored contract.
     */
    public Contract create(Contract contract) {
        return contractRepository.save(contract);
    }

    /**
     * Uses contractRepository to find a contract given its id.
     *
     * @param id - the id of contract to find.
     * @return The contract corresponding to the id.
     */
    public Contract getContract(long id) {
        return contractRepository.getById(id);
    }

    /**
     * Uses contractRepository to check if id represents a contract in database.
     *
     * @param id - the id associated with contract
     * @return boolean representing whether a contract exists.
     */
    public boolean exists(long id) {
        return contractRepository.existsById(id);
    }

}
