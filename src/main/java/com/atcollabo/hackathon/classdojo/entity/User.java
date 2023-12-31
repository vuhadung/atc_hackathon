package com.atcollabo.hackathon.classdojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="USERS")
@Getter @Setter
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phone_number;

    @Column(name = "HASHED_PASSWORD")
    @JsonIgnore
    private String password;

    @Column(name = "CREATED_AT")
    @JsonIgnore
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_AT", columnDefinition = "")
    @JsonIgnore
    private LocalDateTime updatedDate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Fetch(FetchMode.SELECT)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentClass> joinedClasses = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Class> classes = new ArrayList<>();

}