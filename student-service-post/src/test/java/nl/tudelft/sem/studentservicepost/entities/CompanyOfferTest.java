package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompanyOfferTest {

    @Test
    void setChangedOffers() {
        Set<ChangedOffer> set = Set.of(new ChangedOffer("bcde"), new ChangedOffer("fghi"));

        CompanyOffer c = new CompanyOffer();
        c.setChangedOffers(set);
        assertThat(c.getChangedOffers()).isEqualTo(set);
    }

    @Test
    void testToString() {
        CompanyOffer c = new CompanyOffer("bcde");

        assertThat(c.toString()).isEqualTo("CompanyOffer{"
            + "id=" + null
            + ", companyId='" + "bcde" + '\''
            + ", requirementsSet=" + new HashSet<>()
            + ", weeklyHours=" + null
            + ", totalHours=" + null
            + ", expertise=" + new HashSet<>()
            + ", post=" + null
            + '}');
    }

    @Test
    void testEqualsDifferentClass() {
        CompanyOffer c = new CompanyOffer("aaaa");
        String b = "abcde";
        assertThat(c).isNotEqualTo(b);
    }

    @Test
    void testEqualsNull() {
        CompanyOffer c = new CompanyOffer("aaaa");
        assertThat(c).isNotEqualTo(null);
    }

    @Test
    void testEqualsDifferentPost() {
        CompanyOffer c = new CompanyOffer("abcd");
        c.setPost(new Post("a"));
        CompanyOffer d = new CompanyOffer("abcd");
        d.setPost(new Post("b"));
        assertThat(c).isNotEqualTo(d);
    }

    @Test
    void testEqualsDifferentCompany() {
        CompanyOffer c = new CompanyOffer("liao");
        Post p = new Post("a");
        c.setPost(p);
        CompanyOffer d = new CompanyOffer("ziao");
        d.setPost(p);

        assertThat(c).isNotEqualTo(d);
    }

    @Test
    void testEquals() {
        CompanyOffer c = new CompanyOffer("liao");
        Post p = new Post("a");
        c.setPost(p);
        CompanyOffer d = new CompanyOffer("liao");
        d.setPost(p);

        assertThat(c).isEqualTo(d);
    }
}