package nl.tudelft.sem.eureka.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import nl.tudelft.sem.eureka.entities.DummyEntity;
import org.junit.jupiter.api.Test;

class DummyServiceTest {

    @Test
    void getDummy() {
        DummyService dummyService = new DummyService();
        //setup
        List<DummyEntity> expected = List.of(new DummyEntity());

        assertThat(dummyService.getDummy()).isEqualTo(expected);
    }
}