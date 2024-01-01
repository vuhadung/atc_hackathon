package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
@Table(name = "CHECK_ATTENDANCE")
public class Checks {
    @Id @GeneratedValue
    @Column(name = "CHECK_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private User student;

    @ManyToOne
    @JoinColumn(name = "ATTENDANCE_ID")
    private Attendance attendance;

    @Column(name = "CHECKED_IN_AT")
    private LocalDateTime checkedInAt;
}
