package com.atcollabo.hackathon.classdojo.dto;


import com.atcollabo.hackathon.classdojo.entity.Role;
import com.atcollabo.hackathon.classdojo.entity.User;
import com.atcollabo.hackathon.classdojo.entity.UserInfo;
import lombok.*;

import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private String token;

    public UserResponseDTO(UserInfo user, String token) {
        this.fullName = user.fullName();
        this.email = user.email();
        this.phone = user.phoneNumber();
        this.role = user.getAuthorities().toString();
        this.token = token;
    }
}
