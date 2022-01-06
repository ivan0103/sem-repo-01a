package nl.tudelft.sem.studentservicepost.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;


public class Contract {

    private long id;

    private String freelancerId;

    private String companyId;

    private String freelancerName;

    private String companyName;

    private LocalTime hoursPerWeek;

    private float payPerWeek;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate agreementDate;

    public Contract() {
    }

    /**
     * Instantiates a new Contract.
     *
     * @param freelancerId   the freelancer id
     * @param companyId      the company id
     * @param freelancerName the freelancer name
     * @param companyName    the company name
     * @param hoursPerWeek   the hours per week
     * @param payPerWeek     the pay per week
     * @param startDate      the start date
     * @param endDate        the end date
     */
    public Contract(String freelancerId, String companyId, String freelancerName,
                    String companyName, LocalTime hoursPerWeek, float payPerWeek,
                    LocalDate startDate, LocalDate endDate) {

        this.freelancerId = freelancerId;
        this.companyId = companyId;
        this.freelancerName = freelancerName;
        this.companyName = companyName;
        this.hoursPerWeek = hoursPerWeek;
        this.payPerWeek = payPerWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.agreementDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }


    protected void setId(long id) {
        this.id = id;
    }

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalTime getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(LocalTime hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public float getPayPerWeek() {
        return payPerWeek;
    }

    public void setPayPerWeek(float payPerWeek) {
        this.payPerWeek = payPerWeek;
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

    /**
     * Equals method to check of equality of entity to another object using its id.
     *
     * @param o - the other object.
     * @return A boolean representing if they are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Contract) {
            Contract that = (Contract) o;

            return (this.id == that.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}