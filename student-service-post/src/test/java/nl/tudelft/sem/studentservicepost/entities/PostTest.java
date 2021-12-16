package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void setExpertiseSet() {
        Post p = new Post();
        Set<Expertise> setE = Set.of(new Expertise("aaa"), new Expertise("bbb"));
        p.setExpertiseSet(setE);
        assertThat(p.getExpertiseSet()).isEqualTo(setE);
    }

    @Test
    void setCompetencySet() {
        Post p = new Post();
        Set<Competency> setC = Set.of(new Competency("aaa"), new Competency("bbb"));
        p.setCompetencySet(setC);
        assertThat(p.getCompetencySet()).isEqualTo(setC);
    }

    @Test
    void testEquals() {
        Post p = new Post();
        List<Integer> l = new ArrayList<>();
        assertThat(p).isNotEqualTo(l);
    }

    @Test
    void testEqualsNull() {
        Post p = new Post();
        assertThat(p).isNotEqualTo(null);
    }
}