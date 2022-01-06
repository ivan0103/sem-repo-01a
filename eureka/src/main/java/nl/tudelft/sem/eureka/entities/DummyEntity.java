package nl.tudelft.sem.eureka.entities;

import java.util.Objects;

public class DummyEntity {

    private int dummyNumber;

    public DummyEntity() {

    }

    public DummyEntity(int dummyNumber) {
        this.dummyNumber = dummyNumber;
    }

    public int getDummyNumber() {
        return dummyNumber;
    }

    public void setDummyNumber(int dummyNumber) {
        this.dummyNumber = dummyNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DummyEntity that = (DummyEntity) o;
        return dummyNumber == that.dummyNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dummyNumber);
    }

    @Override
    public String toString() {
        return "DummyEntity{"
                + "dummyNumber=" + dummyNumber
                + '}';
    }
}
