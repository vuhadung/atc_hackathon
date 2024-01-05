package com.atcollabo.hackathon.classdojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudentIndexDto {
    private int numClassSessions;
    private List<StudentDto> students;

    public StudentIndexDto(int numClassSessions, List<StudentDto> students) {
        this.numClassSessions = numClassSessions;
        this.students = students;
    }
}
