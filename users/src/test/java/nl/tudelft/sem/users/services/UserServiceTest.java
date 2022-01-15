package nl.tudelft.sem.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import javassist.NotFoundException;
import nl.tudelft.sem.users.entities.Company;
import nl.tudelft.sem.users.entities.Feedback;
import nl.tudelft.sem.users.entities.User;
import nl.tudelft.sem.users.entities.UserFactory;
import nl.tudelft.sem.users.repositories.FeedbackRepository;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private transient UserRepository userRepository;

    @MockBean
    private transient FeedbackRepository feedbackRepository;

    private transient UserService userService;

    private final transient User user1 = new UserFactory().createUser("a", "a", 1.2f, "student");
    private final transient User user2 = new UserFactory().createUser("b", "b", 0.2f, "company");
    private final transient List<User> users = List.of(user1, user2);
    private final transient String role = "company";

    @BeforeEach
    public void setUp() {
        this.userService = new UserService(userRepository, feedbackRepository);
    }

    @Test
    public void testConstructor() {
        assertNotNull(userService);
    }

    @Test
    public void testGetUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(users);
        assertEquals(users, userService.getUsers());
    }

    @Test
    public void testGetOneUser() throws NotFoundException {
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        assertEquals(user1, userService.getOneUser(user1.getNetID()));
    }

    @Test
    public void testAddOneUser() {
        User user3 = new UserFactory().createUser("aa", "gg", 0.2f, role);
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        userService.addUser(user3.getNetID(), user3.getName(), ((Company) user3).getRole());
        List<User> expected = new ArrayList<>(users);
        expected.add(user3);
        Mockito.when(userRepository.findAll()).thenReturn(expected);
        assertEquals(expected, userService.getUsers());
    }

    @Test
    public void testAddFeedback() throws NotFoundException {
        Feedback feedback = new Feedback(1L, "blah", 1, user1);
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        User user3 = new UserFactory().createUser("b", "b", 0.2f, role);
        user3.getFeedbacks().add(feedback);
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(java.util.Optional.of(user2));
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        Mockito.when(feedbackRepository.findTopByOrderByIdDesc()).thenReturn(feedback);
        Feedback userFeedback = userService.addFeedback(user1.getNetID(),
                feedback.getText(), feedback.getRating(), user3.getNetID());
        assertEquals(feedback, userFeedback);
    }

    @Test
    public void testDeleteUser() throws NotFoundException {
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.doNothing().when(feedbackRepository).deleteAll(user1.getFeedbacks());
        Mockito.doNothing().when(userRepository).delete(user1);
        assertEquals(user1, userService.deleteUser(user1.getNetID()));
    }

    @Test
    public void updateUser() throws NotFoundException {
        User user3 = new UserFactory().createUser(user2.getNetID(),
                "aaaaaa", user2.getRating(), ((Company) user2).getRole());
        user3.setName("aaaaaa");
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(java.util.Optional.of(user2));
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        assertEquals(user3, userService.updateUser(user2.getNetID(), "aaaaaa"));
    }

    @Test
    public void testEditFeedback() throws NotFoundException {
        Feedback feedback = new Feedback(1L, "blah", 1, user1);
        User user3 = new UserFactory().createUser("b", "b", 0.2f, role);
        user3.getFeedbacks().add(feedback);
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(java.util.Optional.of(user2));
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        Mockito.when(feedbackRepository.findTopByOrderByIdDesc()).thenReturn(feedback);
        Feedback userFeedback = userService.addFeedback(user1.getNetID(),
                feedback.getText(), feedback.getRating(), user3.getNetID());
        assertEquals(feedback, userFeedback);

        Feedback feedback2 = new Feedback(1L, "b", 1, user1);
        User user4 = new UserFactory().createUser(user3.getNetID(), "b", 0.2f, role);
        user4.getFeedbacks().add(feedback2);
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(user3.getNetID()))
                .thenReturn(java.util.Optional.of(user3));
        Mockito.when(feedbackRepository.findById(feedback.getId()))
                .thenReturn(java.util.Optional.of(feedback));
        Mockito.when(feedbackRepository.save(feedback2)).thenReturn(feedback2);
        Mockito.when(userRepository.save(user4)).thenReturn(user4);
        Feedback userFeedback2 = userService.editFeedback(user1.getNetID(),
                feedback2.getText(), feedback.getRating(), feedback.getId(), user3.getNetID());
        assertEquals(feedback2, userFeedback2);
    }

    @Test
    public void testDeleteFeedback() throws NotFoundException {
        Feedback feedback = new Feedback(1L, "blah", 1, user1);
        User user3 = new UserFactory().createUser("b", "b", 0.2f, role);
        user3.getFeedbacks().add(feedback);
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(java.util.Optional.of(user2));
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        Mockito.when(feedbackRepository.findTopByOrderByIdDesc()).thenReturn(feedback);
        Feedback userFeedback = userService.addFeedback(user1.getNetID(),
                feedback.getText(), feedback.getRating(), user3.getNetID());
        assertEquals(feedback, userFeedback);

        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(user3.getNetID()))
                .thenReturn(java.util.Optional.of(user3));
        Mockito.when(feedbackRepository.findById(feedback.getId()))
                .thenReturn(java.util.Optional.of(feedback));
        Mockito.doNothing().when(feedbackRepository).delete(feedback);
        Mockito.when(userRepository.save(user2)).thenReturn(user2);
        Feedback userFeedback2 = userService.deleteFeedback(user1.getNetID(),
                feedback.getId(), user3.getNetID());
        assertEquals(feedback, userFeedback2);
    }
}
