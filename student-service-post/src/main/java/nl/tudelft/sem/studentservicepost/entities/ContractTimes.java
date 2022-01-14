package nl.tudelft.sem.studentservicepost.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class ContractTimes {

    private LocalTime hoursPerWeek;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate agreementDate;

    public ContractTimes() {
    }

    /**
     * Instantiates a new Contract times.
     *
     * @param hoursPerWeek  the hours per week
     * @param startDate     the start date
     * @param endDate       the end date
     * @param agreementDate the agreement date
     */
    public ContractTimes(LocalTime hoursPerWeek, LocalDate startDate, LocalDate endDate,
                         LocalDate agreementDate) {
        this.hoursPerWeek = hoursPerWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.agreementDate = agreementDate;
    }

    public LocalTime getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(LocalTime hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    @Override
    public String toString() {
        return "ContractTimes{" + "hoursPerWeek=" + hoursPerWeek + ", startDate=" + startDate
               + ", endDate=" + endDate + ", agreementDate=" + agreementDate + '}';
    }
}
