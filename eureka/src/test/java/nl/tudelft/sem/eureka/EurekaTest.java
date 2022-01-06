package nl.tudelft.sem.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest(classes = {Eureka.class})
@TestExecutionListeners
public class EurekaTest {

    @Test
    public void contextLoads() {
    }

}
