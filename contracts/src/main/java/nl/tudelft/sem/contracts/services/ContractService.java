package nl.tudelft.sem.contracts.services;

import nl.tudelft.sem.contracts.entities.Contract;
import nl.tudelft.sem.contracts.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ContractService {
    private ContractRepository contractRepository;

    @Autowired
    public ContractService(@Qualifier("ContractRepository") ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract create(Contract contract) {
        return contractRepository.save(contract);
    }

    public Contract getContract(long id){
        Contract contract = contractRepository.findContractById(id);
        return contract;
    }
}
