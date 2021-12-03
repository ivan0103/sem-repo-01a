package nl.tudelft.sem.contracts.repositories;

import nl.tudelft.sem.contracts.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("ContractRepository")
public interface ContractRepository extends JpaRepository<Contract, Long> {
    /**
     * Saves a contract in the database and then returns it.
     * @param contract - the contract to save.
     * @return The contract that's been saved.
     */
    Contract save(Contract contract);

    /**
     * Returns a contract from the database given that contracts id.
     * @param id - the id of the contract to find.
     * @return The contract.
     */
    @Query(value = "SELECT c FROM Contract c WHERE c.id = ?1")
    Contract findContractById(long id);
}


