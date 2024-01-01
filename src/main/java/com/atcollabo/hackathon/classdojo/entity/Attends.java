package com.atcollabo.hackathon.classdojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ATTEND")
public class Attends {

    @Id @GeneratedValue
    @Column(name = "ATTENDS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private User student;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID")
    private Class _class;

    @Column(name = "SCORE")
    private int score;

    @Column(name = "ATTENDANCE")
    private int attendance;
}
