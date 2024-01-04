package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "CLASSES")
public class Class {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private User teacher;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CODE")
    private String code; // 10-digit or 5-digit code (all number or alphanumeric)

    // Later implementation
//    @OneToMany(mappedBy = "_class")
//    List<Bonus> bonuses = new ArrayList<>();

    @OneToMany(mappedBy = "_class")
    List<Attendance> attendances = new ArrayList<>();

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private ClassStatus status;

}
