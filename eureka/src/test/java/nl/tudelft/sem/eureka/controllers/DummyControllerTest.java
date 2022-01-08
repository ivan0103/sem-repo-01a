package nl.tudelft.sem.eureka.controllers;

import nl.tudelft.sem.eureka.entities.DummyEntity;
import nl.tudelft.sem.eureka.services.DummyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DummyControllerTest {

    DummyController underTest;

    @Mock
    DummyService dummyService;

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