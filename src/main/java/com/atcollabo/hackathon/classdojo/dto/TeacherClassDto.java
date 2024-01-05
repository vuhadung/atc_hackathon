package com.atcollabo.hackathon.classdojo.dto;

import com.atcollabo.hackathon.classdojo.entity.Class;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassDto {

    private Long classId;

    private Long teacherId;

    private String title;

    private String code;

    private LocalDateTime createdAt;

    private int studentCount;

    public TeacherClassDto(Class _class) {
        this.classId = _class.getId();
        this.teacherId = _class.getTeacher().getId();
        this.title = _class.getTitle();
        this.code = _class.getCode();
        this.createdAt = _class.getCreatedAt();
        this.studentCount = _class.getStudentCount();
    }
}
