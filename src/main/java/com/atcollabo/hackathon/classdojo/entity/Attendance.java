package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "ATTENDANCES")
public class Attendance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDANCE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID")
    private Class _class;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @Column(name = "DATETIME")
    private LocalDateTime datetime;
}
