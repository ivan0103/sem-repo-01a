package nl.tudelft.sem.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;


@SpringBootTest(classes = {Gateway.class})
@TestExecutionListeners
public class GatewayTest {

    @Test
    public void contextLoads() {
    }

}
