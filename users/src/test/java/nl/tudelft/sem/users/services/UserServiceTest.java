package nl.tudelft.sem.users.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final transient String userNotFound = "User with this netID does not exist";
    private final transient String userExists = "User with this netID already exists!";
    private final transient String notNullName = "Name cannot be null!";
    private final transient String feedbackNotFound = "Feedback with this id does not exist";
    private final transient User user = new UserFactory().createUser("aa", "gg", 0.2f, role);
    private final transient Feedback feedbackUser2 = new Feedback(1L, "aa", 4, user2);
    private final transient String name = "name";
    private final transient String netID = "netID_";

    @BeforeEach
    public void setUp() {
        this.userService = new UserService(userRepository, feedbackRepository);
        user.setFeedbacks(List.of(feedbackUser2));
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
    public void testGetOneUser() throws NullPointerException {
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.empty());
        assertEquals(user1, userService.getOneUser(user1.getNetID()));
    }

    @Test
    public void testGetOneUserNotFound() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.getOneUser(user.getNetID()));
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testAddOneUser() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        user.setFeedbacks(new ArrayList<>());
        user.setRating(0.0f);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.addUser(user.getNetID(),
                user.getName(), ((Company) user).getRole()));
    }

    @Test
    public void testAddOneExistentUser() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(user.getNetID(), user.getName(), role));
        assertEquals(userExists, thrown.getMessage());
    }

    @Test
    public void testAddOneUserNameNull() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(user.getNetID(), null, role));
        assertEquals(notNullName, thrown.getMessage());
    }

    @Test
    public void testAddFeedback() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        User user3 = new UserFactory().createUser(netID, name, 0.0f,
                new ArrayList<>(), role);
        Feedback feedback = new Feedback(2L, "ccc", 2, user);
        user3.setFeedbacks(List.of(feedback));
        user3.setRating(Float.valueOf(feedback.getRating()));
        assertEquals(Float.valueOf(feedback.getRating()), user3.getRating());
        Mockito.when(userRepository.findById(user3.getNetID()))
                .thenReturn(Optional.of(user3));
        Mockito.when(userRepository.save(user3))
                .thenReturn(user3);
        Feedback feedback2 = new Feedback(1L, "text", 1, user);
        Mockito.when(feedbackRepository.findTopByOrderByIdDesc())
                .thenReturn(feedback2);
        assertEquals(feedback2,
                userService.addFeedback(user.getNetID(), feedback2.getText(),
                        feedback2.getRating(), user3.getNetID()));
        assertEquals(List.of(feedback, feedback2), user3.getFeedbacks());
        assertEquals(1.5f, user3.getRating());
    }

    @Test
    public void testAddFeedbackNonExistingUser() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.addFeedback(user2.getNetID(), "bl", 1, user.getNetID()));
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testAddFeedbackNonExistingReceiver() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.addFeedback(user2.getNetID(), "aacv", 3, user.getNetID()));
        Mockito.verify(userRepository).findById(user2.getNetID());
        Mockito.verify(userRepository).findById(user.getNetID());
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testDeleteUser() {
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(Optional.of(user1));
        Mockito.doNothing().when(feedbackRepository).deleteAll(user1.getFeedbacks());
        Mockito.doNothing().when(userRepository).delete(user1);
        assertEquals(user1, userService.deleteUser(user1.getNetID()));
    }

    @Test
    public void testDeleteUserNonExistentUser() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.deleteUser(user.getNetID()));
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testDeleteUserMoreThanOneUser() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(user, user2));
        assertEquals(1, user.getFeedbacks().size());
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userRepository.save(user2))
                .thenReturn(user2);
        Mockito.doNothing().when(feedbackRepository).deleteAll();
        Mockito.doNothing().when(userRepository).delete(user2);
        assertEquals(user2, userService.deleteUser(user2.getNetID()));
        assertEquals(0, user.getFeedbacks().size());
    }

    @Test
    public void testDeleteUserKeepFeedbacks() {
        user.getFeedbacks().get(0).setUser(user1);
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(user, user2));
        assertEquals(1, user.getFeedbacks().size());
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userRepository.save(user2))
                .thenReturn(user2);
        Mockito.doNothing().when(feedbackRepository).deleteAll();
        Mockito.doNothing().when(userRepository).delete(user2);
        assertEquals(user2, userService.deleteUser(user2.getNetID()));
        assertEquals(1, user.getFeedbacks().size());
    }

    @Test
    public void testUpdateUser() {
        User user3 = new UserFactory().createUser(user2.getNetID(),
                "aaaaaa", user2.getRating(), ((Company) user2).getRole());
        user3.setName("aaaaaa");
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.save(user3)).thenReturn(user3);
        assertEquals(user3, userService.updateUser(user2.getNetID(), "aaaaaa"));
    }

    @Test
    public void testUpdateNonExistentUser() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.updateUser(user.getNetID(), user.getName()));
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testEditFeedback() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        User user3 = new UserFactory().createUser(netID, name,
                0.0f, new ArrayList<>(), role);
        Feedback feedback = new Feedback(1L, "aaaaac", 4, user);
        String text = "text";
        int rating = 6;
        Feedback feedback2 = new Feedback(2L, text, rating, user);
        user3.setFeedbacks(List.of(feedback, feedback2));
        user3.setRating((feedback.getRating() + feedback2.getRating()) / 2.0f);
        Mockito.when(userRepository.findById(user3.getNetID()))
                .thenReturn(Optional.of(user3));
        Mockito.when(feedbackRepository.findById(feedback2.getId()))
                .thenReturn(Optional.of(feedback2));
        Mockito.when(feedbackRepository.save(feedback2))
                .thenReturn(feedback2);
        Mockito.when(userRepository.save(user3))
                .thenReturn(user3);
        String newText = "newText";
        int newRating = 7;
        Feedback feedback3 = new Feedback(feedback2.getId(), feedback2.getText(),
                feedback2.getRating(), user);
        assertEquals(feedback2,
                userService.editFeedback(user.getNetID(), newText,
                        newRating, feedback2.getId(), user3.getNetID()));
        List<Feedback> initialList = List.of(feedback, feedback3);
        assertNotEquals(initialList, user3.getFeedbacks());
        assertEquals(newText, feedback2.getText());
        assertEquals(newRating, feedback2.getRating());
        assertEquals(feedback2, user3.getFeedbacks().get(1));
        assertEquals(feedback2.getRating(), user3.getFeedbacks().get(1).getRating());
        assertEquals(feedback2.getText(), user3.getFeedbacks().get(1).getText());
        assertEquals(5.5f, user3.getRating());
        assertNotEquals(text, user3.getFeedbacks().get(1).getText());
        assertNotEquals(rating, user3.getFeedbacks().get(1).getRating());
        assertNotEquals(text, feedback2.getText());
        assertNotEquals(rating, feedback2.getRating());
    }

    @Test
    public void testEditFeedbackUserNotFound() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.editFeedback(user2.getNetID(), feedbackUser2.getText(),
                        feedbackUser2.getRating(), feedbackUser2.getId(),
                        user.getNetID()));
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testEditFeedbackReceiverNotFound() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.editFeedback(user2.getNetID(), feedbackUser2.getText(),
                        feedbackUser2.getRating(), feedbackUser2.getId(),
                        user.getNetID()));
        Mockito.verify(userRepository).findById(user.getNetID());
        Mockito.verify(userRepository).findById(user2.getNetID());
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testEditFeedbackNoFeedback() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(feedbackRepository.findById(feedbackUser2.getId()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.editFeedback(user2.getNetID(), feedbackUser2.getText(),
                        feedbackUser2.getRating(), feedbackUser2.getId(),
                        user.getNetID()));
        assertEquals(feedbackNotFound, thrown.getMessage());
        Mockito.verify(userRepository).findById(user.getNetID());
        Mockito.verify(userRepository).findById(user2.getNetID());
        Mockito.verify(feedbackRepository).findById(feedbackUser2.getId());
    }

    @Test
    public void testEditFeedbackNoAuthority() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(Optional.of(user1));
        Mockito.when(feedbackRepository.findById(feedbackUser2.getId()))
                .thenReturn(Optional.of(feedbackUser2));
        assertNull(userService.editFeedback(user.getNetID(), feedbackUser2.getText(),
                feedbackUser2.getRating(), feedbackUser2.getId(),
                user1.getNetID()));
        Mockito.verify(userRepository, Mockito.times(2)).findById(user.getNetID());
        Mockito.verify(userRepository, Mockito.times(2)).findById(user1.getNetID());
        Mockito.verify(feedbackRepository, Mockito.times(2)).findById(feedbackUser2.getId());
    }

    @Test
    public void testDeleteFeedback() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        User user3 = new UserFactory().createUser(netID, name,
                0.0f, new ArrayList<>(), role);
        Feedback feedback = new Feedback(1L, "aaa", 4, user);
        Feedback feedback2 = new Feedback(2L, "vvcr", 6, user);
        Feedback feedback3 = new Feedback(3L, "third", 9, user);
        user3.setFeedbacks(List.of(feedback, feedback2, feedback3));
        user3.setRating((feedback.getRating() + feedback2.getRating()
                + feedback3.getRating()) / 3.0f);
        Mockito.when(userRepository.findById(user3.getNetID()))
                .thenReturn(Optional.of(user3));
        Mockito.when(feedbackRepository.findById(feedback2.getId()))
                .thenReturn(Optional.of(feedback2));
        Mockito.doNothing().when(feedbackRepository).delete(feedback2);
        Mockito.when(userRepository.save(user3))
                .thenReturn(user3);
        int initialSize = user3.getFeedbacks().size();
        assertEquals(feedback2,
                userService.deleteFeedback(user.getNetID(),
                        feedback2.getId(), user3.getNetID()));
        assertEquals(6.5f, user3.getRating());
        assertNotEquals(initialSize, user3.getFeedbacks().size());
    }

    @Test
    public void testDeleteFeedbackOnlyOne() {
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        User user3 = new UserFactory().createUser(netID, name,
                0.0f, new ArrayList<>(), role);
        Feedback feedback2 = new Feedback(2L, "vvcr", 6, user);
        user3.setFeedbacks(List.of(feedback2));
        user3.setRating(feedback2.getRating() * 1.0f);
        Mockito.when(userRepository.findById(user3.getNetID()))
                .thenReturn(Optional.of(user3));
        Mockito.when(feedbackRepository.findById(feedback2.getId()))
                .thenReturn(Optional.of(feedback2));
        Mockito.doNothing().when(feedbackRepository).delete(feedback2);
        Mockito.when(userRepository.save(user3))
                .thenReturn(user3);
        int initialSize = user3.getFeedbacks().size();
        assertEquals(feedback2,
                userService.deleteFeedback(user.getNetID(),
                        feedback2.getId(), user3.getNetID()));
        assertEquals(0.0f, user3.getRating());
        assertNotEquals(initialSize, user3.getFeedbacks().size());
    }

    @Test
    public void testDeleteFeedbackUserNotFound() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.deleteFeedback(user2.getNetID(),
                        feedbackUser2.getId(), user.getNetID()));
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testDeleteFeedbackReceiverNotFound() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.deleteFeedback(user2.getNetID(),
                        feedbackUser2.getId(), user.getNetID()));
        Mockito.verify(userRepository).findById(user2.getNetID());
        Mockito.verify(userRepository).findById(user.getNetID());
        assertEquals(userNotFound, thrown.getMessage());
    }

    @Test
    public void testDeleteFeedbackNoFeedBackFound() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        Mockito.when(feedbackRepository.findById(feedbackUser2.getId()))
                .thenReturn(Optional.empty());
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> userService.deleteFeedback(user2.getNetID(),
                        feedbackUser2.getId(), user.getNetID()));
        assertEquals(feedbackNotFound, thrown.getMessage());
        Mockito.verify(userRepository).findById(user2.getNetID());
        Mockito.verify(userRepository).findById(user.getNetID());
        Mockito.verify(feedbackRepository).findById(feedbackUser2.getId());
    }

    @Test
    public void testDeleteFeedbackNoAuthority() {
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findById(user1.getNetID()))
                .thenReturn(Optional.of(user1));
        Mockito.when(feedbackRepository.findById(feedbackUser2.getId()))
                .thenReturn(Optional.of(feedbackUser2));
        assertNull(userService.deleteFeedback(user2.getNetID(),
                feedbackUser2.getId(), user1.getNetID()));
        Mockito.verify(userRepository, Mockito.times(2)).findById(user2.getNetID());
        Mockito.verify(userRepository, Mockito.times(2)).findById(user1.getNetID());
        Mockito.verify(feedbackRepository, Mockito.times(2)).findById(feedbackUser2.getId());
    }

    @Test
    public void testDeleteFeedbackMoreThanOneFeedback() {
        Feedback feedbackUser1 = new Feedback(1L, "aa", feedbackUser2.getRating(), user1);
        List<Feedback> userFeedbacks = new ArrayList<>(user.getFeedbacks());
        userFeedbacks.add(feedbackUser1);
        user.setFeedbacks(userFeedbacks);
        user.setRating(4.0f);
        assertEquals(2, user.getFeedbacks().size());
        float rating = 4.0f;
        assertEquals(rating, user.getRating(), 0.1);
        Mockito.when(userRepository.findById(user2.getNetID()))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findById(user.getNetID()))
                .thenReturn(Optional.of(user));
        Mockito.when(feedbackRepository.findById(feedbackUser2.getId()))
                .thenReturn(Optional.of(feedbackUser2));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.doNothing().when(feedbackRepository).delete(feedbackUser2);
        assertEquals(feedbackUser2, userService.deleteFeedback(user2.getNetID(),
                feedbackUser2.getId(), user.getNetID()));
        assertEquals(1, user.getFeedbacks().size());
        assertEquals(rating, user.getRating());
        Mockito.verify(userRepository, Mockito.times(2)).findById(user2.getNetID());
        Mockito.verify(userRepository, Mockito.times(2)).findById(user.getNetID());
        Mockito.verify(feedbackRepository, Mockito.times(2)).findById(feedbackUser2.getId());
    }
}
