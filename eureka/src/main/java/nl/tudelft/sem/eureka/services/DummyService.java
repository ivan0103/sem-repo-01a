package nl.tudelft.sem.eureka.services;

import java.util.List;
import nl.tudelft.sem.eureka.entities.DummyEntity;


public class DummyService {

    public List<DummyEntity> getDummy() {
        return List.of(new DummyEntity());
    }
}
