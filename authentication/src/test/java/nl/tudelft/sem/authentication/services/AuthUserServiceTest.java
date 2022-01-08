package nl.tudelft.sem.authentication.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javassist.NotFoundException;
import nl.tudelft.sem.authentication.entities.AuthUser;
import nl.tudelft.sem.authentication.repositories.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
@RunWith(SpringRunner.class)
public class AuthUserServiceTest {

    @MockBean
    private transient AuthUserRepository authUserRepository;

    private transient AuthUserService authUserService;

    private final transient String netID = "netID";
    private final transient String password = "password";
    private final transient String student = "student";
    private final transient AuthUser authUser = new AuthUser(netID, password, student);

    @BeforeEach
    public void setUp() {
        authUserService = new AuthUserService(authUserRepository);
    }

    @Test
    public void testConstructor() {
        assertNotNull(authUserService);
    }

    @Test
    public void testAddAuthUser() {
        when(authUserRepository.save(authUser)).thenReturn(authUser);
        when(authUserRepository.findById(authUser.getNetID())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            authUserService.addAuthUser(null, password, student);
        });
        assertEquals("NetID cannot be null!", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class,
                () -> authUserService.addAuthUser(netID, null, student));
        assertEquals("Password cannot be null!", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class,
                () -> authUserService.addAuthUser(netID, password, "dsfdsfds"));
        assertEquals("Invalid role", thrown.getMessage());

        AuthUser authUser2 = authUserService.addAuthUser(netID, password, student);
        assertEquals(authUser2, authUser);
        verify(authUserRepository).save(authUser);

        when(authUserRepository.findById(authUser.getNetID()))
                .thenReturn(java.util.Optional.of(authUser));

        thrown = assertThrows(IllegalArgumentException.class,
                () -> authUserService.addAuthUser(netID, password, student));
        assertEquals("User with this netID already exists!", thrown.getMessage());
        verify(authUserRepository, times(2)).findById(authUser.getNetID());
    }

    @Test
    public void testGetAccount() throws NotFoundException {
        when(authUserRepository.findById(authUser.getNetID()))
                .thenReturn(java.util.Optional.of(authUser));
        AuthUser authUser2 = authUserService.getAccount(authUser.getNetID());
        assertEquals(authUser2, authUser);
        verify(authUserRepository, times(2)).findById(authUser.getNetID());

        when(authUserRepository.findById(authUser.getNetID())).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(NotFoundException.class,
                () -> authUserService.getAccount(authUser.getNetID()));
        assertEquals("User with this netID does not exist!", thrown.getMessage());
        verify(authUserRepository, times(3)).findById(authUser.getNetID());
    }

    @Test
    public void testIsAuthenticated() {
        when(authUserRepository.findById(authUser.getNetID())).thenReturn(Optional.empty());
        assertFalse(authUserService.isAuthenticated(authUser));
        verify(authUserRepository).findById(authUser.getNetID());

        when(authUserRepository.findById(authUser.getNetID()))
                .thenReturn(java.util.Optional.of(authUser));
        assertTrue(authUserService.isAuthenticated(authUser));
        verify(authUserRepository, times(3)).findById(authUser.getNetID());
    }
}
