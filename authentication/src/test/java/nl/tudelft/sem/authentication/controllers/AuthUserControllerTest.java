package nl.tudelft.sem.authentication.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.services.AuthUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class AuthUserControllerTest {

    private final transient String authUrl = "/authentication";
    private final transient String netID = "netID";
    private final transient String password = "password";
    private final transient String student = "student";
    private final transient String addAuthUser = "/addAuthUser/";

    private transient AuthUser authUser = new AuthUser();
    private transient String serializedAuthUser;
    private transient AuthUser authUser2;
    private transient String serializedAuthUser2;

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient AuthUserService authUserService;

    /**
     * Before each method to set up the tests.
     *
     * @throws NotFoundException if we cannot find the user
     */

    @BeforeEach
    public void setUp() throws NotFoundException {
        authUser.setNetID(netID);
        authUser.setPassword(password);
        authUser.setRole(student);
        authUser2 = new AuthUser("s", "c", "company");

        try {
            ObjectMapper om = new ObjectMapper();
            serializedAuthUser = om.writeValueAsString(authUser);
            serializedAuthUser2 = om.writeValueAsString(authUser2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Assertions.fail();
        }

        when(authUserService.addAuthUser(null, password, student))
                .thenThrow(new IllegalArgumentException("NetID cannot be null!"));

        when(authUserService.addAuthUser(netID, null, student))
                .thenThrow(new IllegalArgumentException("Password cannot be null!"));

        when(authUserService.addAuthUser(netID, password, "comp"))
                .thenThrow(new IllegalArgumentException("Invalid role"));

        when(authUserService.addAuthUser(netID, password, student))
                .thenReturn(authUser);

        when(authUserService.getAccount("111"))
                .thenThrow(new NotFoundException("User with this netID does not exist!"));

        when(authUserService.getAccount(netID))
                .thenReturn(authUser);

        when(authUserService.isAuthenticated(authUser))
                .thenReturn(true);

        when(authUserService.isAuthenticated(authUser2))
                .thenReturn(false);
    }

    @Test
    public void testAddAuthUser() {
        String url = authUrl + addAuthUser
                + netID + "/" + password + "/" + student;
        try {
            this.mockMvc.perform(post(url))
                    .andDo(print())
                    .andExpect(status().isCreated());
            verify(authUserService).addAuthUser(netID, password, student);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in adding a new authenticated user!");
        }
    }

    @Test
    public void testAddUserInvalidNetID() {
        String url = authUrl + addAuthUser
                + null + "/" + password + "/" + student;
        try {
            this.mockMvc.perform(post(url))
                    .andDo(print())
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            assertEquals("NetID cannot be null!", e.getMessage());
            verify(authUserService).addAuthUser(null, password, student);
        }
    }

    @Test
    public void testAddUserInvalidPassword() {
        String url = authUrl + addAuthUser
                + netID + "/" + null + "/" + student;
        try {
            this.mockMvc.perform(post(url))
                    .andDo(print())
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            assertEquals("Password cannot be null!", e.getMessage());
            verify(authUserService).addAuthUser(netID, null, student);
        }
    }

    @Test
    public void testAddUserInvalidRole() {
        String url = authUrl + addAuthUser
                + netID + "/" + password + "/" + "blah";
        try {
            this.mockMvc.perform(post(url))
                    .andDo(print())
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            assertEquals("Invalid role", e.getMessage());
            verify(authUserService).addAuthUser(netID, password, "blah");
        }
    }

    @Test
    public void testRetrieveAccount() {
        String url = authUrl + "/ADMIN?netID=" + netID;
        try {
            this.mockMvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().isFound());
            verify(authUserService).getAccount(netID);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in retrieving account");
        }
    }

    @Test
    public void testAccountNotRetrieved() throws NotFoundException {
        String url = authUrl + "/ADMIN?netID=" + "ddsfs";
        try {
            this.mockMvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().isFound());
        } catch (Exception e) {
            assertEquals("User with this netID does not exist!", e.getMessage());
            verify(authUserService).getAccount("ddsfs");
        }
    }

    @Test
    public void testIsAuthenticated() {
        String url = authUrl + "/isAuthenticated";
        try {
            this.mockMvc.perform(get(url).content(serializedAuthUser)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(authUserService).isAuthenticated(authUser);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception in checking whether user is authenticated");
        }
    }

    @Test
    public void testIsNotAuthenticated() {
        String url = authUrl + "/isAuthenticated";
        try {
            this.mockMvc.perform(get(url).content(serializedAuthUser2)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
            verify(authUserService).isAuthenticated(authUser2);
        }
    }
}
