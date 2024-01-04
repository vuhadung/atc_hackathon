//package com.atcollabo.hackathon.classdojo.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter @Setter
//@Table(name = "PARTICIPATE_IN")
//public class StudentBonus {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "PARTICIPATION_ID")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "STUDENT_ID")
//    private Student student;
//
//    @ManyToOne
//    @JoinColumn(name = "BONUS_ID")
//    private Bonus bonus;
//
//    @Column(name = "ANSWER")
//    private String answer;
//}
