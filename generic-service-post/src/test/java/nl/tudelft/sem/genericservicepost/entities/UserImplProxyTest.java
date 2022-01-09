package nl.tudelft.sem.genericservicepost.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserImplProxyTest {

    private final transient String netid = "netid";
    private final transient String name = "name";
    @Mock
    private transient RestTemplateBuilder restTemplateBuilder;
    @Mock
    private transient RestTemplate restTemplate;
    @Mock
    private transient ResponseEntity<UserImpl> responseEntity;
    private transient UserImpl user;
    private transient UserImplProxy userImplProxy;
    private transient Feedback feedback;

    @BeforeEach
    void setup() {
        user = new UserImpl();
        user.setNetID(netid);
        user.setName(name);
        user.setRating(3.0f);
        feedback = new Feedback("good", 3, user);
        user.setFeedbacks(List.of(feedback));
        when(restTemplate.getForEntity(anyString(), eq(UserImpl.class), anyString())).thenReturn(
                responseEntity);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        userImplProxy = new UserImplProxy(netid, restTemplateBuilder);

    }

    @Test
    void badResponseNetId() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        assertThat(userImplProxy.getNetID()).isNull();
    }

    @Test
    void getNetID() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(user);
        assertThat(userImplProxy.getNetID()).isEqualTo(netid);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(UserImpl.class), eq(netid));
    }

    @Test
    void getNetAlreadyThere() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(user);
        userImplProxy.getNetID();
        assertThat(userImplProxy.getNetID()).isEqualTo(netid);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(UserImpl.class), eq(netid));
    }

    @Test
    void badResponseName() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        assertThat(userImplProxy.getName()).isNull();
    }

    @Test
    void getName() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(user);
        assertThat(userImplProxy.getName()).isEqualTo(name);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(UserImpl.class), eq(netid));
    }

    @Test
    void badResponseRating() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        assertThat(userImplProxy.getRating()).isNull();
    }

    @Test
    void getRating() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(user);
        assertThat(userImplProxy.getRating()).isEqualTo(3.0f);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(UserImpl.class), eq(netid));
    }

    @Test
    void badResponseFeedbacks() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        assertThat(userImplProxy.getFeedbacks()).isNull();
    }

    @Test
    void getFeedbacks() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(user);
        assertThat(userImplProxy.getFeedbacks()).containsExactly(feedback);
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(UserImpl.class), eq(netid));
    }
}