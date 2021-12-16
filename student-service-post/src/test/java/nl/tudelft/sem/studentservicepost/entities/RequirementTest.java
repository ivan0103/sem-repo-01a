package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class RequirementTest {


    @Test
    void constructor() {
        Requirement c = new Requirement();
        assertThat(c).isNotNull();
    }

    @Test
    void equalsDifferentClass() {
        Requirement c = new Requirement();
        Expertise e = new Expertise();
        assertThat(c).isNotEqualTo(e);
    }

    @Test
    void equalsNull() {
        Requirement c = new Requirement();
        assertThat(c).isNotEqualTo(null);
    }

    @Test
    void offerSetTest() {
        Requirement c = new Requirement();
        Set<CompanyOffer> cSet = Set.of(new CompanyOffer("aaaa"), new CompanyOffer("bbbb"));
        c.setCompanyOfferSet(cSet);
        assertThat(c.getCompanyOfferSet()).isEqualTo(cSet);
    }

    @Test
    void toStrings() {

        Requirement c = new Requirement("aabb");

        assertThat(c.toString()).isEqualTo("Requirement{"
            + "requirementString='" + "aabb" + '\''
            + '}');

    }

    @Test
    void setRequirementString() {

        Requirement r = new Requirement();
        r.setRequirementString("abcd");
        assertThat(r.getRequirementString()).isEqualTo("abcd");
    }
}