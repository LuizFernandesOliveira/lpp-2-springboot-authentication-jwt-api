package com.authentication.integrations;

import com.authentication.controllers.UserController;
import com.authentication.models.User;
import com.authentication.services.UserService;
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

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

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

}
