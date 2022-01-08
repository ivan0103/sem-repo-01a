package nl.tudelft.sem.gateway.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.ArrayList;
import nl.tudelft.sem.gateway.entities.AuthUser;
import nl.tudelft.sem.gateway.entities.CommunicationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;


class GatewayServiceTest {

    private transient AuthUser user;
    private transient GatewayService underTest;
    private final transient String baboon = "Baboon";
    private final transient String baboon2 = "Baboon2";
    private final transient String student = "Student";

    @Mock
    private transient RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        user = new AuthUser(baboon, baboon2, student);

        underTest = new GatewayService(restTemplate);
    }

    @Test
    @Disabled
    void findByUsername() {
        given(restTemplate.getForObject("http://users/{Baboon}", AuthUser.class)).willReturn(user);
    }

    @Test
    @Disabled
    void createAccountNoRole() {
        CommunicationEntity communicationEntity = new CommunicationEntity(
                "Chimp",
                "Bill",
                "Chimp1",
                null
                );

        IllegalArgumentException testCase = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.createAccount(communicationEntity)
        );

        //then
        verifyNoInteractions(restTemplate);

        assertThat(testCase.getMessage()).isEqualTo("Role mustn't be null!");

    }





    /*@Disabled
    @Test
    void loadUserByUsernameTest() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(student));

        User expected = new User(baboon, baboon2, authorities);

        //when
        UserDetails testCase = underTest.loadUserByUsername(baboon);

        //then
        assertThat(testCase).isEqualTo(expected);
    }

    @Disabled
    @Test
    void getAuthUserByUsernameTest() {
        //when
        AuthUser testCase = underTest.getAuthUserByUsername(baboon);

        //then
        assertThat(testCase).isEqualTo(user);
    }*/
}