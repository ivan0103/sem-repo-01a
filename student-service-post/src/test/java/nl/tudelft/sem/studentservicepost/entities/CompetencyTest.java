package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.Test;

class CompetencyTest {


    @Test
    void equalsStrangeLetters() {

        Competency c1 = new Competency("comp1");
        Competency c2 = new Competency("Com P      1");
        assertThat(c1).isEqualTo(c2);
    }

    @Test
    void constructor() {
        Competency c = new Competency();
        assertThat(c).isNotNull();
    }

    @Test
    void equalsDifferentClass() {
        Competency c = new Competency();
        Expertise e = new Expertise();
        assertThat(c).isNotEqualTo(e);
    }

    @Test
    void equalsNull() {
        Competency c = new Competency();
        assertThat(c).isNotEqualTo(null);
    }

    @Test
    void postSetTest() {
        Competency c = new Competency();
        Set<Post> postSet = Set.of(new Post("aaaa"), new Post("bbbb"));
        c.setPostSet(postSet);
        assertThat(c.getPostSet()).isEqualTo(postSet);
    }

    @Test
    void searchableStrings() {

        Competency c = new Competency();
        c.setCompetencyString("aAbBBB");
        assertThat(c.getCompetencyString()).isEqualTo("aAbBBB");
        assertThat(c.getSearchableString()).isEqualTo("aabbbb");
        c.setSearchableString("abcd");
        assertThat(c.getSearchableString()).isEqualTo("abcd");

    }

}