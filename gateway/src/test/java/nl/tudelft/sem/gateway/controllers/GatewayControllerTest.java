package nl.tudelft.sem.gateway.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import nl.tudelft.sem.gateway.entities.AuthUser;
import nl.tudelft.sem.gateway.entities.CommunicationEntity;
import nl.tudelft.sem.gateway.services.GatewayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GatewayControllerTest {

    private transient GatewayController underTest;

    @Mock
    private transient GatewayService gatewayService;




    @BeforeEach
    void setUp() {
        underTest = new GatewayController(gatewayService);
    }

    @Test
    void greetAuthenticatedUsers() {
        //given
        String expected = "HOORAY! You have successfully logged in!";

        //when
        String testCase = underTest.greetAuthenticatedUsers();

        //then
        assertThat(testCase).isEqualTo(expected);
    }

    @Test
    void createAccount() {
        //setup
        CommunicationEntity communicationEntity = new CommunicationEntity(
                "baboon",
                "Bob",
                "baboon2",
                "student"
        );

        AuthUser authUser = new AuthUser("baboon", "baboon2", "student");
        ResponseEntity<AuthUser> expected = new ResponseEntity<>(authUser, HttpStatus.CREATED);

        //given
        given(gatewayService.createAccount(communicationEntity)).willReturn(authUser);

        //when
        ResponseEntity<AuthUser> testCase = underTest.createAccount(communicationEntity);

        //then
        assertThat(testCase).isEqualTo(expected);
    }
}