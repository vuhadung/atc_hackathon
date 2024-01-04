package com.atcollabo.hackathon.classdojo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Username can not be empty!")
    private String username;

    @NotEmpty(message = "Password can not be empty!")
    private String password;
}
