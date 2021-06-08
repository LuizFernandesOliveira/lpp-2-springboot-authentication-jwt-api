package com.authentication.integrations;

import com.authentication.config.jwt.JwtFilter;
import com.authentication.config.security.Web;
import com.authentication.controllers.AuthController;
import com.authentication.repositories.UserRepository;
import com.authentication.services.AuthService;
import com.authentication.services.UserDetailsServiceImpl;
import com.authentication.services.UserService;
import com.authentication.utils.AccountCredentials;
import com.authentication.utils.JwtUtil;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.authController);
    }

    @Test
    public void testNotGetTokenWithEmailOrPasswordIncorrectly_ReturnNotFound() {
        AccountCredentials credentials = AccountCredentials.builder()
                .email("luizfernandesoliveiraoficial@gmail.com")
                .password("luiz12")
                .build();

        RestAssuredMockMvc
                .given().auth().none()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when().post("/token")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetToken_ReturnOk() {
        AccountCredentials credentials = AccountCredentials.builder()
                .email("luizfernandesoliveiraoficial@gmail.com")
                .password("luiz123").build();

        RestAssuredMockMvc
                .given().auth().none()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when().post("/token")
                .then().statusCode(HttpStatus.OK.value());
    }

}
