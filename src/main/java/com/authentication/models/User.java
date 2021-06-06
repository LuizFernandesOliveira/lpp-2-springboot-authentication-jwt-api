package com.authentication.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "'name' is required")
    @NotBlank(message = "'name' is invalid")
    private String name;

    @NotNull(message = "'email' is required")
    @Email(message = "'email' is invalid")
    private String email;

    @NotNull(message = "'password' is required")
    @Size(min = 6, message = "password not is greater then 6 characters")
    private String password;
}
