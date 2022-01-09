package nl.tudelft.sem.gateway.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.nullable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.gateway.entities.AuthUser;
import nl.tudelft.sem.gateway.entities.CommunicationEntity;
import nl.tudelft.sem.gateway.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class GatewayServiceTest {
    private transient String calledAuthUrl;
    private transient GatewayService underTest;
    private transient User user;


    @Mock
    private transient RestTemplate restTemplate;
    private transient String name;
    private transient String netId;
    private transient String role;
    private transient String password;

    @BeforeEach
    void setUp() {
        underTest = new GatewayService(restTemplate);

        name = "Ben";
        netId = "Bonobo";
        role = "student";
        password = "Bonobo2";
        user = new User(netId, name, 2f, new ArrayList<>(), role);

        calledAuthUrl = "http://AUTHENTICATION/authentication//ADMIN?netID=Bonobo";
    }

    @Test
    void findByUsernameInvalidUserName() {
        given(restTemplate.getForObject(
                calledAuthUrl,
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
        AuthUser authUser = new AuthUser(netId, password, role);

        Mono<UserDetails> expected = Mono.just(
                new org.springframework.security.core.userdetails.User(
                        authUser.getNetID(),
                        new BCryptPasswordEncoder().encode(
                                authUser.getPassword()),
                        List.of(new SimpleGrantedAuthority(authUser.getRole()))
                )
        );

        given(restTemplate.getForObject(
                calledAuthUrl,
                AuthUser.class)).willReturn(authUser);

        //when
        Mono<UserDetails> testCase = underTest.findByUsername(netId);

        //then
        verifyNoMoreInteractions(restTemplate);

        assertThat(testCase.toString()).isEqualTo(expected.toString());
    }


    @Test
    void createAccountRoleIsNull() {
        //when
        IllegalArgumentException testCase = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    CommunicationEntity communicationEntity = new CommunicationEntity(
                            netId,
                            name,
                            password,
                            ""
                    );
                    underTest.createAccount(communicationEntity);
                }
        );

        //then
        verifyNoMoreInteractions(restTemplate);

        assertThat(testCase.getMessage()).isEqualTo("Role must not be null!");
    }

    @Test
    void createAccountIDTakenInUsers() {
        //given
        given(restTemplate.getForObject("http://USERS/users/Bonobo", User.class)).willReturn(user);

        //when
        IllegalArgumentException testCase = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    CommunicationEntity communicationEntity = new CommunicationEntity(
                            netId,
                            name,
                            password,
                            role
                    );

                    underTest.createAccount(communicationEntity);
                }
        );

        //then
        verifyNoMoreInteractions(restTemplate);

        assertThat(testCase.getMessage()).isEqualTo("Net ID already exists!");
    }

    @Test
    void createAccountIDTakenInAuthUsers() {
        //setup
        AuthUser authUser = new AuthUser(netId, password, role);


        //given
        given(restTemplate.getForObject("http://USERS/users/Bonobo", User.class)).willReturn(null);
        given(restTemplate.getForObject(
                calledAuthUrl,
                AuthUser.class)).willReturn(authUser);

        //when
        IllegalArgumentException testCase = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    CommunicationEntity communicationEntity = new CommunicationEntity(
                            netId,
                            name,
                            password,
                            role
                    );
                    underTest.createAccount(communicationEntity);
                }
        );

        //then
        verifyNoMoreInteractions(restTemplate);

        assertThat(testCase.getMessage()).isEqualTo("NetID does not exist!");
    }

    @Test
    void createAccountSuccessful() {
        //setup
        CommunicationEntity communicationEntity = new CommunicationEntity(
                netId,
                name,
                password,
                role
        );

        AuthUser authUser = new AuthUser(netId, password, role);
        ArgumentCaptor<String> stringArgumentCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpMethod> httpMethodArgumentCaptor1  =
                ArgumentCaptor.forClass(HttpMethod.class);

        //given
        given(restTemplate.getForObject("http://USERS/users/Bonobo", User.class)).willReturn(null);
        given(restTemplate.getForObject(
                calledAuthUrl,
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
                "http://USERS/users/addUser/"
                        + communicationEntity.getNetID() + "/"
                        + communicationEntity.getName() + "/"
                        + communicationEntity.getRole()
        );

        assertThat(capturedHttpMethod1.get(0)).isEqualTo(HttpMethod.POST);
        assertThat(capturedHttpMethod1.get(1)).isEqualTo(HttpMethod.POST);
        assertThat(testCase).isEqualTo(authUser);
    }

}