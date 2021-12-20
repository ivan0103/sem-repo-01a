package nl.tudelft.sem.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;


@SpringBootTest(classes = {Users.class})
@TestExecutionListeners
public class UsersTest {

    @Test
    public void contextLoads() {
    }

}
