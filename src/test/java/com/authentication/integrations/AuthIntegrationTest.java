package com.authentication.integrations;

import static org.assertj.core.api.Assertions.assertThat;

import com.authentication.Lpp2SpringbootAuthenticationJwtApiApplication;
import com.authentication.exceptions.Message;
import com.authentication.repositories.UserRepository;
import com.authentication.utils.AccountCredentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(classes = Lpp2SpringbootAuthenticationJwtApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    private UserRepository userRepository;

    private final String URI = "/token";

    @Test
    public void testNotGetTokenWithCredentialsIncorrectly_ReturnNotFound() throws Exception {
        AccountCredentials credentials = AccountCredentials.builder()
                .email("admin@gmail.com")
                .password("admin12")
                .build();

        Message messageResponse = Message.builder()
                .message("Invalide email or password").build();
        String expectMessageResponse = this.mapToJson(messageResponse);

        HttpEntity<AccountCredentials> entity = new HttpEntity<AccountCredentials>(credentials, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);
        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseInJson).isEqualTo(expectMessageResponse);
    }

    @Test
    public void testGetTokenWithCredentialsCorrectly_ReturnOk() {
        AccountCredentials credentials = AccountCredentials.builder()
                .email("admin@gmail.com")
                .password("admin123").build();

        HttpEntity<AccountCredentials> entity = new HttpEntity<AccountCredentials>(credentials, headers);
        ResponseEntity<String> response = testRestTemplate
                .exchange(url(URI), HttpMethod.POST, entity, String.class);

        HttpStatus status = response.getStatusCode();
        String responseInJson = response.getBody();
        assertThat(status).isEqualTo(HttpStatus.OK);
        assertThat(responseInJson).contains("token");
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }

}
