package com.authentication.utils;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentials {

    private String email;
    private String password;

}
