package nl.tudelft.sem.eureka.controllers;

import java.util.List;
import nl.tudelft.sem.eureka.entities.DummyEntity;
import nl.tudelft.sem.eureka.services.DummyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "DUM")
public class DummyController {

    private final transient DummyService dummyService;

    public DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GetMapping
    public List<DummyEntity> getUsers() {
        return dummyService.getDummy();
    }
}
