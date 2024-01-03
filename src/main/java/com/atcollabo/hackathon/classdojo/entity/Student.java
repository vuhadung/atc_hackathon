package com.atcollabo.hackathon.classdojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "STUDENTS")
public class Student extends User {

    @OneToMany(mappedBy = "student")
    private List<StudentClass> joinedClasses = new ArrayList<>();


}
