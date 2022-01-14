package nl.tudelft.sem.studentservicepost.entities;

public class ContractParties {
    private String freelancerId;

    private String companyId;

    private String freelancerName;

    private String companyName;

    public ContractParties() {
    }


    /**
     * Instantiates a new Contract parties.
     *
     * @param freelancerId   the freelancer id
     * @param companyId      the company id
     * @param freelancerName the freelancer name
     * @param companyName    the company name
     */
    public ContractParties(String freelancerId, String companyId, String freelancerName,
                           String companyName) {
        this.freelancerId = freelancerId;
        this.companyId = companyId;
        this.freelancerName = freelancerName;
        this.companyName = companyName;
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

    @Override
    public String toString() {
        return "ContractParties{" + "freelancerId='" + freelancerId + '\'' + ", companyId='"
               + companyId + '\'' + ", freelancerName='" + freelancerName + '\'' + ", companyName='"
               + companyName + '\'' + '}';
    }
}
