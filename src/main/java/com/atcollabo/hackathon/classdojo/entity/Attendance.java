package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "ATTENDANCES")
public class Attendance {

    @Id @GeneratedValue
    @Column(name = "ATTENDANCE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID")
    private Class _class;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private User teacher;

    @Column(name = "DUE_DATE")
    private LocalDateTime due_date;
}
