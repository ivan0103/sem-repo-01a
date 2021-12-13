package nl.tudelft.sem.users.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import nl.tudelft.sem.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private final transient User user1 = new UserFactory().createUser("a", "a", 1.2f, "student");
    private final transient User user2 = new UserFactory().createUser("b", "b", 0.2f, "company");
    private final transient List<User> users = List.of(user1, user2);
    private final transient String url = "/users";

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FeedbackRepository feedbackRepository;

    private final transient UserController userController = new UserController(userService);

    @Test
    public void testConstructor() {
        assertNotNull(userController);
    }

    @Test
    public void testGetUsers() {
        userService.addUser(user1.getNetID(), user1.getName(), ((Student) user1).getRole());
        userService.addUser(user2.getNetID(), user2.getName(), ((Company) user2).getRole());

        when(userService.getUsers()).thenReturn(users);

        try {
            this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andReturn();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
