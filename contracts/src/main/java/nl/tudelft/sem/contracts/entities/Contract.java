package nl.tudelft.sem.contracts.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "contract")
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
    private long freelancerId;

    @Column(name = "companyId")
    private long companyId;

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


    public Contract(long freelancerId, long companyId, String freelancerName,
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
    }

    public long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
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



}