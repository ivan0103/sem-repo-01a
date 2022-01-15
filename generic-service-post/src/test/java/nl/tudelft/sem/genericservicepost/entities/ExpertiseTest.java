package nl.tudelft.sem.genericservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class ExpertiseTest {

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
        GenericPost g1 = new GenericPost();
        GenericPost g2 = new GenericPost();
        g1.setId(1L);
        g2.setId(2L);
        Set<GenericPost> postSet = Set.of(g1, g2);
        c.setGenericPostSet(postSet);
        assertThat(c.getGenericPostSet()).isEqualTo(postSet);
    }

    @Test
    void equalsSame() {
        Expertise e = new Expertise("abcd");
        assertThat(e).isEqualTo(e);
    }
}