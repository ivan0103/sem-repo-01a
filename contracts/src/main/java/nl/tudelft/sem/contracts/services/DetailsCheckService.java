package nl.tudelft.sem.contracts.services;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;

@Service
public class DetailsCheckService {

    /**
     * This checks if duration of contract is not more than 6 months and end and start date
     * are in the correct order.
     *
     * @param startDate - the date when contract starts.
     * @param endDate - the date when contract ends.
     * @return Boolean representing if the dates are valid.
     */
    public boolean checkContractDates(LocalDate startDate, LocalDate endDate) {
        //checks if start date is before end date.
        if (endDate.isBefore(startDate)) {
            return false;
        }

        long datesDurationDays = DAYS.between(startDate, endDate);

        //checks if contract duration is more than 6 months
        return !(datesDurationDays > 182.5);
    }

    /**
     * Checks if the freelancer working hours are not over 20h per week.
     *
     * @param hoursPerWeek - the hours the freelancer works per week.
     * @return Boolean representing if working hours are valid.
     */
    public boolean checkContractWorkHours(LocalTime hoursPerWeek) {
        LocalTime limit = LocalTime.of(20, 0);
        return !(hoursPerWeek.isAfter(limit));
    }

}
