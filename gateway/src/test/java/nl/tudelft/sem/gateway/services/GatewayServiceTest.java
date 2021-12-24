package nl.tudelft.sem.gateway.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import nl.tudelft.sem.gateway.entities.AuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

class GatewayServiceTest {

    AuthUser user;

    GatewayService underTest;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        user = new AuthUser("Baboon", "Baboon2", "Student");

        underTest = new GatewayService(restTemplate);

        given(restTemplate.getForObject("http://users/{Baboon}", AuthUser.class)).willReturn(user);
    }

    @Disabled
    @Test
    void loadUserByUsernameTest() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Student"));

        User expected = new User("Baboon", "Baboon2", authorities);

        //when
        UserDetails testCase = underTest.loadUserByUsername("Baboon");

        //then
        assertThat(testCase).isEqualTo(expected);
    }

    @Disabled
    @Test
    void getAuthUserByUsernameTest() {
        //when
        AuthUser testCase = underTest.getAuthUserByUsername("Baboon");

        //then
        assertThat(testCase).isEqualTo(user);
    }
}