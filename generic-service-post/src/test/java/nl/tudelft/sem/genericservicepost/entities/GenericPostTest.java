package nl.tudelft.sem.genericservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class GenericPostTest {

    @Test
    void setExpertiseSet() {
        GenericPost p = new GenericPost();
        Set<Expertise> setE = Set.of(new Expertise("aaa"), new Expertise("bbb"));
        p.setExpertiseSet(setE);
        assertThat(p.getExpertiseSet()).isEqualTo(setE);
    }

    @Test
    void setHours() {
        GenericPost p = new GenericPost();
        p.setHoursPerWeek(12);
        assertThat(p.getHoursPerWeek()).isEqualTo(12);
    }

    @Test
    void setDuration() {
        GenericPost p = new GenericPost();
        p.setDuration(4);
        assertThat(p.getDuration()).isEqualTo(4);
    }

    @Test
    void setStudentSet() {
        GenericPost p = new GenericPost();
        Set<UserImpl> users = new HashSet<>();
        users.add(new UserImpl());
        p.setStudentSet(users);
        assertThat(p.getStudentSet()).isEqualTo(users);
    }

    @Test
    void setStudent() {
        GenericPost p = new GenericPost();
        UserImpl student = new UserImpl();
        p.setSelectedStudent(student);
        assertThat(p.getSelectedStudent()).isEqualTo(student);
    }

    @Test
    void setStudentOfferSet() {
        GenericPost p = new GenericPost();
        Set<StudentOffer> set = new HashSet<>();
        set.add(new StudentOffer());
        p.setStudentOfferSet(set);
        assertThat(p.getStudentOfferSet()).isEqualTo(set);
    }

    @Test
    void testEquals() {
        GenericPost p = new GenericPost();
        List<Integer> l = new ArrayList<>();
        assertThat(p).isNotEqualTo(l);
    }

    @Test
    void testEqualsNull() {
        GenericPost p = new GenericPost();
        assertThat(p).isNotEqualTo(null);
    }
}
