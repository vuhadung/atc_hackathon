package com.atcollabo.hackathon.classdojo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Accessors(fluent = true, chain = true)
@Getter
@Setter
public class UserInfo extends User {
    private String fullName;
    private String email;
    private String phoneNumber;
    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
