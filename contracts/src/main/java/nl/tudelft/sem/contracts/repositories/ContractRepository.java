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
     * Saves a contract in the database and then returns it.
     *
     * @param contract - the contract to save.
     * @return The contract that's been saved.
     */
    //Contract save(Contract contract);

    /**
     * Returns a contract from the database given that contracts id.
     *
     * @param id - the id of the contract to find.
     * @return The contract.
     */
    Contract getById(long id);

    /**
     * Checks if a contract corresponding to given id exists in database.
     *
     * @param id - the id corresponding to contract instance.
     * @return boolean representing if contract exists.
     */
    //boolean existsById(long id);


    /*@Modifying
    @Transactional
    @Query(value = "UPDATE Contract c SET c.endDate =?2 WHERE c.id =?1")
    void updateEndDateById(long id, LocalDate endDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Contract c SET c.startDate =?2 WHERE c.id =?1")
    void updateStartDateById(long id, LocalDate startDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Contract c SET c.payPerWeek =?2 WHERE c.id =?1")
    void updatePayById(long id, float payPerWeek);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Contract c SET c.hoursPerWeek =?2 WHERE c.id =?1")
    void updateWorkHoursById(long id, LocalTime hoursPerWeek);*/
}


