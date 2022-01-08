package nl.tudelft.sem.eureka.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class DummyEntityTest {

    private transient DummyEntity dummyEntity;

    @BeforeEach
    void setUp() {
        dummyEntity = new DummyEntity(2);
    }

    @Test
    void constructorEmpty() {
        DummyEntity dummyEntity2 = new DummyEntity();

        assertThat(dummyEntity2).isNotNull();
    }

    @Test
    void getDummyNumber() {
        assertThat(dummyEntity.getDummyNumber()).isEqualTo(2);
    }

    @Test
    void setDummyNumber() {
        dummyEntity.setDummyNumber(3);
        assertThat(dummyEntity.getDummyNumber()).isEqualTo(3);

        dummyEntity.setDummyNumber(2);
    }

    @Test
    void testEqualsSameInstance() {
        assertThat(dummyEntity.equals(dummyEntity)).isTrue();
    }

    @Test
    void testEqualsWrongClass() {
        assertThat(dummyEntity.equals("Boo")).isFalse();
    }

    @Test
    void testEqualsTrue() {
        DummyEntity dummyEntity2 = new DummyEntity();
        dummyEntity2.setDummyNumber(2);

        assertThat(dummyEntity.equals(dummyEntity2)).isTrue();
    }

    @Test
    void testEqualsFalse() {
        DummyEntity dummyEntity2 = new DummyEntity();

        assertThat(dummyEntity.equals(dummyEntity2)).isFalse();
    }

    @Test
    void testHashCode() {
        int expected = Objects.hash(dummyEntity.getDummyNumber());

        assertThat(dummyEntity.hashCode()).isEqualTo(expected);
    }

    @Test
    void testToString() {
        String expected = "DummyEntity{"
                + "dummyNumber=" + dummyEntity.getDummyNumber()
                + '}';

        assertThat(dummyEntity.toString()).isEqualTo(expected);
    }
}