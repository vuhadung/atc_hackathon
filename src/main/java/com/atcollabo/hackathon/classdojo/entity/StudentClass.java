package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "STUDENT_CLASS")
public class StudentClass {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_CLASS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private User student;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID")
    private Class _class;

    @Column(name = "ATTENDANCE")
    private int attendance;

}
