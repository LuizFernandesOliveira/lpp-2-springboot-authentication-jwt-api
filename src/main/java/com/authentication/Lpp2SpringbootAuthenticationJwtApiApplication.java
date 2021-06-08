package com.authentication;

import com.authentication.models.User;
import com.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@AllArgsConstructor
public class Lpp2SpringbootAuthenticationJwtApiApplication {
    private final UserRepository userRepository;

    @PostConstruct
    public void initUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .id(1L)
                    .name("Luiz")
                    .email("luizfernandesoliveiraoficial@gmail.com")
                    .password("luiz123").build());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Lpp2SpringbootAuthenticationJwtApiApplication.class, args);
    }

}
