package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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

    @Test
    void testEqualsSameUntilPrice() {
        Post p = new Post("1");
        Post q = new Post("1");
        p.setPricePerHour(new BigDecimal("14.00"));
        q.setPricePerHour(new BigDecimal("14.00"));
        Set<Expertise> e = Set.of(new Expertise("exp"), new Expertise("exp1"));
        p.setExpertiseSet(e);
        assertThat(p).isNotEqualTo(q);
    }

    @Test
    void testEqualsSameUntilExpertise() {
        Post p = new Post("2");
        Post q = new Post("2");
        p.setPricePerHour(new BigDecimal("12.00"));
        q.setPricePerHour(new BigDecimal("12.00"));
        Set<Expertise> e = Set.of(new Expertise("abc"), new Expertise("bcd"));
        p.setExpertiseSet(e);
        q.setExpertiseSet(e);
        p.setCompetencySet(Set.of());
        q.setCompetencySet(Set.of(new Competency("efg")));
        assertThat(p).isNotEqualTo(q);
    }

    @Test
    void testEqualsSameEverything() {
        Post p = new Post("3");
        Post q = new Post("3");
        p.setPricePerHour(new BigDecimal("18.00"));
        q.setPricePerHour(new BigDecimal("18.00"));
        Set<Expertise> e = Set.of(new Expertise("abc"), new Expertise("bcd"));
        p.setExpertiseSet(e);
        q.setExpertiseSet(e);
        p.setCompetencySet(Set.of(new Competency("efg")));
        q.setCompetencySet(Set.of(new Competency("efg")));
        assertThat(p).isEqualTo(q);
    }
}