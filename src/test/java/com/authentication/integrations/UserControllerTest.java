package com.authentication.integrations;

import com.authentication.config.jwt.JwtFilter;
import com.authentication.controllers.UserController;
import com.authentication.models.User;
import com.authentication.repositories.UserRepository;
import com.authentication.services.UserDetailsServiceImpl;
import com.authentication.services.UserService;
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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.userController);
    }

    @Test
    public void testCreateUser_ReturnOk() {
        User userForCreate = User.builder()
                .name("Luiz Fernandes de Oliveira")
                .email("luizfernandesoliveiraoficial@gmail.com")
                .password("luiz123").build();

        User userCreated = User.builder()
                .id(1L)
                .name(userForCreate.getName())
                .email(userForCreate.getEmail())
                .password(userForCreate.getPassword())
                .build();

        Mockito.when(userService.create(userForCreate)).thenReturn(userCreated);

        RestAssuredMockMvc
                .given().auth().none()
                    .contentType(ContentType.JSON)
                    .body(userForCreate)
                .when().post("/users")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testCreateNotUserWithoutEmail_ReturnBadRequest() {
        User userForCreate = User.builder()
                .name("Luiz Fernandes de Oliveira")
                .password("luiz123").build();

        RestAssuredMockMvc
                .given().auth().none()
                .contentType(ContentType.JSON)
                .body(userForCreate)
                .when().post("/users")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreateNotUserWithoutName_ReturnBadRequest() {
        User userForCreate = User.builder()
                .email("luizfernandesoliveiraoficial@gmail.com")
                .password("luiz123").build();

        RestAssuredMockMvc
                .given().auth().none()
                .contentType(ContentType.JSON)
                .body(userForCreate)
                .when().post("/users")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreateNotUserWithoutPassword_ReturnBadRequest() {
        User userForCreate = User.builder()
                .name("Luiz Fernandes de Oliveira")
                .email("luizfernandesoliveiraoficial@gmail.com")
                .build();

        RestAssuredMockMvc
                .given().auth().none()
                .contentType(ContentType.JSON)
                .body(userForCreate)
                .when().post("/users")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
