//package com.atcollabo.hackathon.classdojo.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "BONUSES")
//public class Bonus {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "BONUS_ID")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "CLASS_ID")
//    private Class _class;
//
//    private String title;
//
//    @ManyToOne
//    @JoinColumn(name = "TEACHER_ID")
//    private Teacher teacher;
//
//    @Column(name = "POINT_VAL")
//    private int point_value;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "TYPE")
//    private BonusType type;
//
//    @Column(name = "CORRECT_ANS")
//    private String correct_answer;
//
//    @Column(name = "DUE_DATE")
//    private LocalDateTime due_date;
//}
