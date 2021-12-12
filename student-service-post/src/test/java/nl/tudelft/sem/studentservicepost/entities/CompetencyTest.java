package nl.tudelft.sem.studentservicepost.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CompetencyTest {


    @Test
    void equalsStrangeLetters() {

        Competency c1 = new Competency("comp1");
        Competency c2 = new Competency("Com P      1");
        assertThat(c1).isEqualTo(c2);
    }

}