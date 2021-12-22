package nl.tudelft.sem.registry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;


@SpringBootTest(classes = {Registry.class})
@TestExecutionListeners
public class RegistryTest {

    @Test
    public void contextLoads() {
    }

}
