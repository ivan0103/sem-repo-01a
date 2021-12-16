package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class ExpertiseTest {


    @Test
    void equalsStrangeLetters() {

        Expertise c1 = new Expertise("comp1");
        Expertise c2 = new Expertise("Com P      1");
        assertThat(c1).isEqualTo(c2);
    }

    @Test
    void constructor() {
        Expertise c = new Expertise();
        assertThat(c).isNotNull();
    }

    @Test
    void equalsDifferentClass() {
        Expertise e = new Expertise();
        Competency c = new Competency();
        assertThat(e).isNotEqualTo(c);
    }

    @Test
    void equalsNull() {
        Expertise c = new Expertise();
        assertThat(c).isNotEqualTo(null);
    }

    @Test
    void postSetTest() {
        Expertise c = new Expertise();
        Set<Post> postSet = Set.of(new Post("aaaa"), new Post("bbbb"));
        c.setPostSet(postSet);
        assertThat(c.getPostSet()).isEqualTo(postSet);
    }

    @Test
    void searchableStrings() {

        Expertise c = new Expertise();
        c.setExpertiseString("aAbBBB");
        assertThat(c.getExpertiseString()).isEqualTo("aAbBBB");
        assertThat(c.getSearchableString()).isEqualTo("aabbbb");
        c.setSearchableString("abcd");
        assertThat(c.getSearchableString()).isEqualTo("abcd");

    }

    @Test
    void equalsSame() {
        Expertise e = new Expertise("abcd");
        assertThat(e).isEqualTo(e);
    }

    @Test
    void offerSet() {
        Set<CompanyOffer> cSet = Set.of(new CompanyOffer("aaaa"), new CompanyOffer("bbbb"));
        Expertise e = new Expertise();
        e.setOfferSet(cSet);
        assertThat(e.getOfferSet()).isEqualTo(cSet);
    }

}