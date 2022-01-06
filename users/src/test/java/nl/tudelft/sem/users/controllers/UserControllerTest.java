package nl.tudelft.sem.users.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.Student;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import nl.tudelft.sem.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() {
        userService.addUser(user1.getNetID(), user1.getName(), ((Student) user1).getRole());
        userService.addUser(user2.getNetID(), user2.getName(), ((Company) user2).getRole());
    }

    @Test
    public void testConstructor() {
        assertNotNull(userController);
    }

    @Test
    public void testGetUsers() throws Exception {
        when(userService.getUsers()).thenReturn(users);
        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetOneUser() throws Exception {
        when(userService.getOneUser(user1.getNetID())).thenReturn(user1);
        this.mockMvc.perform(get(url + "/" + user1.getNetID()))
                    .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testAddOneUser() throws Exception {
        User createdUser = new UserFactory().createUser("cc", "vvv", 20.0f, "student");
        when(userService.addUser(createdUser.getNetID(),
                createdUser.getName(), ((Student) createdUser).getRole())).thenReturn(createdUser);
        this.mockMvc.perform(post(url + "/" + createdUser.getNetID()
                + "/" + createdUser.getName() + "/" + ((Student) createdUser).getRole()))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddFeedback() throws Exception {
        Feedback feedback = new Feedback(1L, "blah", 1, user1);
        user2.addFeedback(feedback);
        when(userService.addFeedback(feedback.getUser().getNetID(), feedback.getText(),
                feedback.getRating(), user2.getNetID())).thenReturn(feedback);
        this.mockMvc.perform(post(url + "/" + feedback.getUser().getNetID()
                + "/" + feedback.getText() + "/" + feedback.getRating() + "/" + user2.getName()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        userService.deleteUser(user1.getNetID());
        when(userService.deleteUser(user1.getNetID())).thenReturn(user1);
        this.mockMvc.perform(delete(url + "/" + user1.getNetID()))
                    .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        user1.setName("New name");
        userService.updateUser(user1.getNetID(), "New name");
        when(userService.updateUser(user1.getNetID(), "New name")).thenReturn(user1);
        this.mockMvc.perform(put(url + "/" + user1.getNetID() + "/" + user1.getName()))
                    .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testEditFeedback() throws Exception {
        Feedback feedback = new Feedback(1L, "blah", 1, user1);
        user2.addFeedback(feedback);
        when(userService.addFeedback(feedback.getUser().getNetID(), feedback.getText(),
                feedback.getRating(), user2.getNetID())).thenReturn(feedback);
        this.mockMvc.perform(post(url + "/" + feedback.getUser().getNetID()
                        + "/" + feedback.getText() + "/" + feedback.getRating()
                        + "/" + user2.getName()))
                .andDo(print()).andExpect(status().isOk());

        user2.removeFeedback(feedback);
        feedback.setRating(-1);
        user2.addFeedback(feedback);
        when(userService.editFeedback(user1.getNetID(), feedback.getText(),
                feedback.getRating(), feedback.getId(), user2.getNetID())).thenReturn(feedback);
        this.mockMvc.perform(put(url + "/" + user1.getNetID() + "/" + feedback.getText()
            + "/" + feedback.getRating() + "/" + feedback.getId() + "/" + user2.getNetID()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testDeleteFeedback() throws Exception {
        Feedback feedback = new Feedback(1L, "blah", 1, user1);
        user2.addFeedback(feedback);
        when(userService.addFeedback(feedback.getUser().getNetID(), feedback.getText(),
                feedback.getRating(), user2.getNetID())).thenReturn(feedback);
        this.mockMvc.perform(post(url + "/" + feedback.getUser().getNetID()
                        + "/" + feedback.getText() + "/" + feedback.getRating()
                        + "/" + user2.getName()))
                .andDo(print()).andExpect(status().isOk());

        user2.removeFeedback(feedback);
        when(userService.deleteFeedback(user1.getNetID(), feedback.getId(), user2.getNetID()))
                .thenReturn(feedback);
        this.mockMvc.perform(delete(url + "/" + user1.getNetID() + "/" + feedback.getId()
                        + "/" + user2.getNetID()))
                .andDo(print()).andExpect(status().isOk());
    }

}
