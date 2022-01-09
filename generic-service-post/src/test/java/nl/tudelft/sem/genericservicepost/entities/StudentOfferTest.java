package nl.tudelft.sem.genericservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StudentOfferTest {


    @Test
    void testToString() {
        StudentOffer c = new StudentOffer("bcde");

        assertThat(c.toString()).isEqualTo(
                "StudentOffer{"
                        + "id=" + "null"
                        + ", studentId='" + "bcde" + '\''
                        + ", genericPost=" + "null"
                        + '}');
    }

    @Test
    void testEqualsDifferentClass() {
        StudentOffer c = new StudentOffer("aaaa");
        String b = "abcde";
        assertThat(c).isNotEqualTo(b);
    }

    @Test
    void testEqualsNull() {
        StudentOffer c = new StudentOffer("aaaa");
        assertThat(c).isNotEqualTo(null);
    }

    @Test
    void testEqualsDifferentPost() {
        StudentOffer c = new StudentOffer("abcd");
        GenericPost cg = new GenericPost();
        cg.setId(1L);
        c.setGenericPost(cg);
        StudentOffer d = new StudentOffer("abcd");
        GenericPost cd = new GenericPost();
        cd.setId(2L);
        d.setGenericPost(cd);
        assertThat(c).isNotEqualTo(d);
    }

    @Test
    void testEqualsDifferentCompany() {
        StudentOffer c = new StudentOffer("liao");
        GenericPost p = new GenericPost();
        c.setGenericPost(p);
        StudentOffer d = new StudentOffer("ziao");
        d.setGenericPost(p);

        assertThat(c).isNotEqualTo(d);
    }

    @Test
    void testEquals() {
        StudentOffer c = new StudentOffer("liao");
        GenericPost p = new GenericPost();
        c.setGenericPost(p);
        StudentOffer d = new StudentOffer("liao");
        d.setGenericPost(p);

        assertThat(c).isEqualTo(d);
    }
}