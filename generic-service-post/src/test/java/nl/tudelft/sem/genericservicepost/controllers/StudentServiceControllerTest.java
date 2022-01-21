package nl.tudelft.sem.genericservicepost.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.genericservicepost.entities.Expertise;
import nl.tudelft.sem.genericservicepost.entities.GenericPost;
import nl.tudelft.sem.genericservicepost.entities.UserImpl;
import nl.tudelft.sem.genericservicepost.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class StudentServiceControllerTest {

    private transient GenericPost genericPost = new GenericPost();

    private transient UserImpl user = new UserImpl();

    private transient Set<UserImpl> userSet;

    @Mock
    private transient StudentService studentService;

    @InjectMocks
    private transient StudentServiceController studentServiceController;

    @BeforeEach
    void setup() {
        genericPost.setId(1L);
        genericPost.setAuthor("helloTest");
        genericPost.setHoursPerWeek(3);
        genericPost.setDuration(4);
        genericPost.setExpertiseSet(Set.of(new Expertise("skill")));

        user.setName("Marie");
        user.setNetID("abc123");
        userSet = Set.of(user);

        genericPost.setStudentSet(userSet);
    }

    @Test
    void getStudentsByPostTest() {
        when(studentService.retrieveStudentsInPost(eq(genericPost))).thenReturn(new HashSet<>(List.of(user)));

        ResponseEntity<Set<UserImpl>> result = studentServiceController.getStudentsByPost(genericPost);
        ResponseEntity<Set<UserImpl>> test = new ResponseEntity<>(new HashSet<>(List.of(user)), HttpStatus.FOUND);
        assertThat(result).isEqualTo(test);
    }

}
