package nl.tudelft.sem.studentservicepost.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * The type Contract.
 */
public class Contract {

    private long id;

    private float payPerWeek;

    private final transient ContractParties contractParties;

    private final transient ContractTimes contractTimes;

    /**
     * Instantiates a new Contract.
     */
    public Contract() {
        this.contractParties = new ContractParties();
        this.contractTimes = new ContractTimes();
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

        this.contractParties =
            new ContractParties(freelancerId, companyId, freelancerName, companyName);
        this.contractTimes = new ContractTimes(hoursPerWeek, startDate, endDate, LocalDate.now());
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets id.
     *
     * @param id the id
     */
    protected void setId(long id) {
        this.id = id;
    }

    /**
     * Gets freelancer id.
     *
     * @return the freelancer id
     */
    public String getFreelancerId() {
        return this.contractParties.getFreelancerId();
    }

    /**
     * Sets freelancer id.
     *
     * @param freelancerId the freelancer id
     */
    public void setFreelancerId(String freelancerId) {
        this.contractParties.setFreelancerId(freelancerId);
    }

    /**
     * Gets company id.
     *
     * @return the company id
     */
    public String getCompanyId() {
        return this.contractParties.getCompanyId();
    }

    /**
     * Sets company id.
     *
     * @param companyId the company id
     */
    public void setCompanyId(String companyId) {
        this.contractParties.setCompanyId(companyId);
    }

    /**
     * Gets freelancer name.
     *
     * @return the freelancer name
     */
    public String getFreelancerName() {
        return this.contractParties.getFreelancerName();
    }

    /**
     * Sets freelancer name.
     *
     * @param freelancerName the freelancer name
     */
    public void setFreelancerName(String freelancerName) {
        this.contractParties.setFreelancerName(freelancerName);
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return this.contractParties.getCompanyName();
    }

    /**
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.contractParties.setCompanyName(companyName);
    }

    /**
     * Gets hours per week.
     *
     * @return the hours per week
     */
    public LocalTime getHoursPerWeek() {
        return this.contractTimes.getHoursPerWeek();
    }

    /**
     * Sets hours per week.
     *
     * @param hoursPerWeek the hours per week
     */
    public void setHoursPerWeek(LocalTime hoursPerWeek) {
        this.contractTimes.setHoursPerWeek(hoursPerWeek);
    }

    /**
     * Gets pay per week.
     *
     * @return the pay per week
     */
    public float getPayPerWeek() {
        return payPerWeek;
    }

    /**
     * Sets pay per week.
     *
     * @param payPerWeek the pay per week
     */
    public void setPayPerWeek(float payPerWeek) {
        this.payPerWeek = payPerWeek;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return this.contractTimes.getStartDate();
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.contractTimes.setStartDate(startDate);
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return this.contractTimes.getEndDate();
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.contractTimes.setEndDate(endDate);
    }

    /**
     * Gets agreement date.
     *
     * @return the agreement date
     */
    public LocalDate getAgreementDate() {
        return this.contractTimes.getAgreementDate();
    }

    /**
     * Sets agreement date.
     *
     * @param agreementDate the agreement date
     */
    public void setAgreementDate(LocalDate agreementDate) {
        this.contractTimes.setAgreementDate(agreementDate);
    }

    /**
     * Static method to build contract from offer.
     *
     * @param offer     the offer
     * @param startDate the start date of the contract
     * @param endDate   the end date of the contract
     * @return the contract
     */
    public static Contract buildFromOffer(CompanyOffer offer,
                                          @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate startDate,
                                          @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate endDate) {
        Post post = offer.getPost();
        return new Contract(post.getAuthor(), offer.getCompanyId(), post.getAuthor(),
            offer.getCompanyId(), LocalTime.of(offer.getWeeklyHours(), 0),
            offer.getPricePerHour().floatValue(), startDate, endDate);
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

    @Override
    public String toString() {
        return "Contract{" + "id=" + id + ", payPerWeek=" + payPerWeek + ", contractParties="
               + contractParties + ", contractTimes=" + contractTimes + '}';
    }
}