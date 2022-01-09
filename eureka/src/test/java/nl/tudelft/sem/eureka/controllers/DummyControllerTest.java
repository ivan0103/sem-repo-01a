package nl.tudelft.sem.eureka.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import nl.tudelft.sem.eureka.entities.DummyEntity;
import nl.tudelft.sem.eureka.services.DummyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DummyControllerTest {

    private transient DummyController underTest;

    @Mock
    private transient DummyService dummyService;

    @BeforeEach
    void setUp() {
        underTest = new DummyController(dummyService);
    }

    @Test
    void getUsers() {
        //given
        List<DummyEntity> expected = List.of(new DummyEntity());
        given(dummyService.getDummy()).willReturn(expected);

        //when
        List<DummyEntity> testCase = underTest.getUsers();

        //then
        assertThat(testCase).isEqualTo(expected);
    }

}