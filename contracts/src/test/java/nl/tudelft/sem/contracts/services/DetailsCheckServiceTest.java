package nl.tudelft.sem.contracts.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetailsCheckServiceTest {

    private transient DetailsCheckService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DetailsCheckService();
    }

    @Test
    void checkContractDatesTrue() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 5, 1);

        assertThat(underTest.checkContractDates(start, end)).isTrue();
    }

    @Test
    void checkContractDatesFalse() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 8, 1);

        assertThat(underTest.checkContractDates(start, end)).isFalse();
    }

    @Test
    void checkContractWorkHoursTrue() {
        LocalTime workHours = LocalTime.of(16, 0);

        assertThat(underTest.checkContractWorkHours(workHours)).isTrue();
    }

    @Test
    void checkContractWorkHoursFalse() {
        LocalTime workHours = LocalTime.of(21, 0);

        assertThat(underTest.checkContractWorkHours(workHours)).isFalse();
    }
}