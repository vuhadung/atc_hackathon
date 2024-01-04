package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter @Setter
@Table(name = "STUDENT_ATTENDANCE")
public class StudentAttendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ATTENDANCE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private User student;

    @ManyToOne
    @JoinColumn(name = "ATTENDANCE_ID")
    private Attendance attendanceActivity;

    @Column(name = "IS_PRESENT")
    private boolean isPresent;
}
