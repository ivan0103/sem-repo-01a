package nl.tudelft.sem.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;


@SpringBootTest(classes = {Authentication.class})
@TestExecutionListeners
public class AuthenticationTest {

    @Test
    public void contextLoads() {
    }

}
