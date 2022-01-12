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

    /**
     * Instantiates a new Contract.
     */
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
        return freelancerId;
    }

    /**
     * Sets freelancer id.
     *
     * @param freelancerId the freelancer id
     */
    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    /**
     * Gets company id.
     *
     * @return the company id
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * Sets company id.
     *
     * @param companyId the company id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets freelancer name.
     *
     * @return the freelancer name
     */
    public String getFreelancerName() {
        return freelancerName;
    }

    /**
     * Sets freelancer name.
     *
     * @param freelancerName the freelancer name
     */
    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets company name.
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets hours per week.
     *
     * @return the hours per week
     */
    public LocalTime getHoursPerWeek() {
        return hoursPerWeek;
    }

    /**
     * Sets hours per week.
     *
     * @param hoursPerWeek the hours per week
     */
    public void setHoursPerWeek(LocalTime hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
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
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets agreement date.
     *
     * @return the agreement date
     */
    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    /**
     * Sets agreement date.
     *
     * @param agreementDate the agreement date
     */
    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    /**
     * Static method to build contract from offer.
     *
     * @param offer     the offer
     * @param startDate the start date of the contract
     * @param endDate   the end date of the contract
     * @return the contract
     */
    public static Contract buildFromOffer(
            CompanyOffer offer,
            @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Post post = offer.getPost();
        Contract contract = new Contract(post.getAuthor(),
                offer.getCompanyId(), post.getAuthor(), offer.getCompanyId(),
                LocalTime.of(offer.getWeeklyHours().intValue(), 0),
                offer.getPricePerHour().floatValue(), startDate, endDate);
        return contract;
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