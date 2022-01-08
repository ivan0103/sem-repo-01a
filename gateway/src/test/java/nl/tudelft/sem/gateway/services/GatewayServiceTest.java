package nl.tudelft.sem.gateway.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;
import nl.tudelft.sem.gateway.entities.AuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class GatewayServiceTest {
    private transient GatewayService underTest;


    @Mock
    private transient RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        underTest = new GatewayService(restTemplate);
    }

    @Test
    void findByUsernameInvalidUserName() {
        given(restTemplate.getForObject(
                "http://AUTHENTICATION/authentication//ADMIN?netID=Bonobo",
                AuthUser.class)).willReturn(null);

        //when
        Mono<UserDetails> testCase = underTest.findByUsername("Bonobo");

        //then
        verifyNoMoreInteractions(restTemplate);

        assertThat(testCase).isNull();
    }

    @Test
    void findByUsernameSuccessful() {
        //setup
        AuthUser authUser = new AuthUser("Bonobo", "Bonobo2", "student");

        Mono<UserDetails> expected = Mono.just(
                new org.springframework.security.core.userdetails.User(
                        authUser.getNetID(),
                        new BCryptPasswordEncoder().encode(authUser.getPassword()),
                        List.of(new SimpleGrantedAuthority(authUser.getRole()))
                )
        );

        given(restTemplate.getForObject(
                "http://AUTHENTICATION/authentication//ADMIN?netID=Bonobo",
                AuthUser.class)).willReturn(authUser);

        //when
        Mono<UserDetails> testCase = underTest.findByUsername("Bonobo");

        //then
        verifyNoMoreInteractions(restTemplate);

        assertThat(testCase.toString()).isEqualTo(expected.toString());
    }


    /*@Test
    void createAccountIDTakenInUsers() {
        //setup
        CommunicationEntity communicationEntity = new CommunicationEntity(
                "Bonobo",
                "Ben",
                "Bonobo2",
                "student"
        );

        AuthUser authUser = new AuthUser("Bonobo", "Bonobo2", "student");


        //given
        given(restTemplate.getForObject("http://USERS/users/Bonobo", User.class)).willReturn(user);

        //when
        AuthUser testCase = underTest.createAccount(communicationEntity);

        //then
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void createAccountIDTakenInAuthUsers() {
        //setup
        CommunicationEntity communicationEntity = new CommunicationEntity(
                "Bonobo",
                "Ben",
                "Bonobo2",
                "student"
        );

        AuthUser authUser = new AuthUser("Bonobo", "Bonobo2", "student");


        //given
        given(restTemplate.getForObject("http://USERS/users/Bonobo", User.class)).willReturn(null);
        given(restTemplate.getForObject(
                "http://AUTHENTICATION/authentication//ADMIN?netID=Bonobo",
                AuthUser.class)).willReturn(authUser);

        //when
        AuthUser testCase = underTest.createAccount(communicationEntity);

        //then
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void createAccountSuccessful() {
        //setup
        CommunicationEntity communicationEntity = new CommunicationEntity(
                "Bonobo",
                "Ben",
                "Bonobo2",
                "student"
        );

        AuthUser authUser = new AuthUser("Bonobo", "Bonobo2", "student");
        ArgumentCaptor<String> stringArgumentCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpMethod> httpMethodArgumentCaptor1  =
                ArgumentCaptor.forClass(HttpMethod.class);

        //given
        given(restTemplate.getForObject("http://USERS/users/Bonobo", User.class)).willReturn(null);
        given(restTemplate.getForObject(
                "http://AUTHENTICATION/authentication//ADMIN?netID=Bonobo",
                AuthUser.class)).willReturn(null);

        //when
        AuthUser testCase = underTest.createAccount(communicationEntity);

        //then
        verify(restTemplate, times(2)).execute(
                stringArgumentCaptor1.capture(),
                httpMethodArgumentCaptor1.capture(),
                nullable(RequestCallback.class), nullable(ResponseExtractor.class));

        List<String> capturedUrl1 = stringArgumentCaptor1.getAllValues();
        List<HttpMethod> capturedHttpMethod1 = httpMethodArgumentCaptor1.getAllValues();


        assertThat(capturedUrl1.get(0)).isEqualTo(
                "http://AUTHENTICATION/authentication/addAuthUser/"
                        + communicationEntity.getNetID() + "/"
                        + communicationEntity.getPassword() + "/"
                        + communicationEntity.getRole()
        );

        assertThat(capturedUrl1.get(1)).isEqualTo(
                "http:/http://USERS/users//addUser/"
                        + communicationEntity.getNetID() + "/"
                        + communicationEntity.getPassword() + "/"
                        + communicationEntity.getRole()
        );

        assertThat(capturedHttpMethod1.get(0)).isEqualTo(HttpMethod.POST);
        assertThat(capturedHttpMethod1.get(1)).isEqualTo(HttpMethod.POST);
        assertThat(testCase).isEqualTo(authUser);
    }
*/




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