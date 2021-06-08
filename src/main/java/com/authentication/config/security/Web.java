package com.authentication.config.security;

import com.authentication.models.User;
import com.authentication.repositories.UserRepository;
import com.authentication.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Web extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new Jwt("/token", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth, UserRepository userRepository) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .email("luizfernandesoliveiraoficial@gmail.com")
                    .name("Luiz Fernandes de Oliveira")
                    .password(passwordEncoder().encode("luiz123"))
                    .build());
        }
        auth.userDetailsService(
                email -> userRepository.findByEmail(email).map(UserDetailsImpl::new).orElse(null)
        ).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
