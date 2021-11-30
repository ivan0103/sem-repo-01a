package nl.tudelft.sem.contracts.repositories;

import nl.tudelft.sem.contracts.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ContractRepository")
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract save(Contract contract);
}
