package com.atcollabo.hackathon.classdojo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.atcollabo.hackathon.classdojo.entity.User;
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

    @NotEmpty(message = "Full Name can not be empty!")
    private String fullName;

    @NotEmpty(message = "Email can not be empty!")
    @Pattern(regexp = "/\\S+@\\S+\\.\\S+/", message = "Email is invalid!")
    private String email;

    @NotEmpty(message = "Phone number can not be empty!")
    @Pattern(regexp = "/(84|0[3|5|7|8|9])+([0-9]{8})\\b/g", message = "Phone number is invalid!")
    private String phoneNumber;

    @NotEmpty(message = "Password can not be empty!")
    private String password;

    public User getUserFromDto() {
        User user = new User();
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setFullName(this.fullName);
        user.setPhone_number(this.phoneNumber);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(this.password));
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return user;
    }

}
