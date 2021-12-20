package nl.tudelft.sem.contracts.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.transaction.Transactional;
import nl.tudelft.sem.contracts.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("ContractRepository")
public interface ContractRepository extends JpaRepository<Contract, Long> {


    /**
     * Returns a contract from the database given that contracts id.
     *
     * @param id - the id of the contract to find.
     * @return The contract.
     */
    Contract getById(long id);


}


