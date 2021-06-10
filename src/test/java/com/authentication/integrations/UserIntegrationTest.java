package com.authentication.integrations;

import com.authentication.Lpp2SpringbootAuthenticationJwtApiApplication;
import com.authentication.exceptions.Message;
import com.authentication.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Lpp2SpringbootAuthenticationJwtApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private final HttpHeaders headers = new HttpHeaders();

    private final String URI = "/users";

    @Test
    public void testCreateUserWithAllInputsCorrectly_ReturnOk() {
        User userForCreate = User.builder()
                .name("Luiz Fernandes de Oliveira")
                .email("luizfernandesoliveiraoficial@gmail.com")
                .password("luiz123").build();

        HttpEntity<User> entity = new HttpEntity<User>(userForCreate, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);

        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.CREATED);
        assertThat(responseInJson).contains(userForCreate.getEmail());
        assertThat(responseInJson).contains(userForCreate.getName());
        assertThat(responseInJson).doesNotContain(userForCreate.getPassword());
    }

    @Test
    public void testNotCreateUserWithEmailExists_ReturnBadRequest() throws JsonProcessingException {
        User userForCreate = User.builder()
                .name("Luiz Hacker")
                .email("admin@gmail.com")
                .password("admin123Hacker").build();

        Message messageResponse = Message.builder()
                .message("'email' exists").build();
        String expectMessageResponse = this.mapToJson(messageResponse);

        HttpEntity<User> entity = new HttpEntity<User>(userForCreate, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);

        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.ALREADY_REPORTED);
        assertThat(responseInJson).contains(expectMessageResponse);
    }

    @Test
    public void testNotCreateUserWithoutEmail_ReturnBadRequest() throws JsonProcessingException {
        User userForCreate = User.builder()
                .name("Luiz Fernandes de Oliveira")
                .password("luiz123").build();

        Message messageResponse = Message.builder()
                .message("'email' is required").build();
        String expectMessageResponse = this.mapToJson(messageResponse);

        HttpEntity<User> entity = new HttpEntity<User>(userForCreate, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);

        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseInJson).contains(expectMessageResponse);
    }

    @Test
    public void testNotCreateUserWithoutPassword_ReturnBadRequest() throws JsonProcessingException {
        User userForCreate = User.builder()
                .name("Luiz Fernandes de Oliveira")
                .email("luizfernandesoliveiraoficial@gmail.com").build();

        Message messageResponse = Message.builder()
                .message("'password' is required").build();
        String expectMessageResponse = this.mapToJson(messageResponse);

        HttpEntity<User> entity = new HttpEntity<User>(userForCreate, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);

        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseInJson).contains(expectMessageResponse);
    }

    @Test
    public void testNotCreateUserWithoutName_ReturnBadRequest() throws JsonProcessingException {
        User userForCreate = User.builder()
                .email("luizfernandesoliveiraoficial@gmail.com")
                .password("luiz123").build();

        Message messageResponse = Message.builder()
                .message("'name' is required").build();
        String expectMessageResponse = this.mapToJson(messageResponse);

        HttpEntity<User> entity = new HttpEntity<User>(userForCreate, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);

        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();

        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseInJson).contains(expectMessageResponse);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }

}
