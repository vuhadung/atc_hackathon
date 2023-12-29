package com.atcollabo.hackathon.classdojo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.atcollabo.hackathon.classdojo.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDto {

    @NotEmpty(message = "Username can not be empty!")
    @Size(max = 32, message = "Username can not exceed 32 characters!")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9_]*", message = "Username is invalid!")
    private String username;

    @NotEmpty(message = "Password can not be empty!")
    private String password;

    public User getUserFromDto() {
        User user = new User();
        user.setUsername(username);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return user;
    }

}
