package nl.tudelft.sem.contracts.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @SequenceGenerator(
            name = "contract_sequence",
            sequenceName = "contract_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contract_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(name = "freelancerId")
    private String freelancerId;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "freelancerName")
    private String freelancerName;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "hoursPerWeek")
    private LocalTime hoursPerWeek;

    @Column(name = "payPerWeek")
    private float payPerWeek;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "agreementDate")
    private final LocalDate agreementDate;


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
        this. companyName = companyName;
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