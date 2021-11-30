package nl.tudelft.sem.contracts.services;

import nl.tudelft.sem.contracts.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    private ContractRepository contractRepository;

    @Autowired
    public ContractService(@Qualifier("ContractRepository") ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

}
