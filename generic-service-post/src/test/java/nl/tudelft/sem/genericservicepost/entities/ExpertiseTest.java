package nl.tudelft.sem.genericservicepost.entities;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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
    void equalsNull() {
        Expertise c = new Expertise();
        assertThat(c).isNotEqualTo(null);
    }

    @Test
    void postSetTest() {
        Expertise c = new Expertise();
        Set<GenericPost> postSet = Set.of(new GenericPost(), new GenericPost());
        c.setGenericPostSet(postSet);
        assertThat(c.getGenericPostSet()).isEqualTo(postSet);
    }

    @Test
    void equalsSame() {
        Expertise e = new Expertise("abcd");
        assertThat(e).isEqualTo(e);
    }
}