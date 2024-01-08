package com.atcollabo.hackathon.classdojo.dto;

import com.atcollabo.hackathon.classdojo.entity.Class;
import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClassResponseDto {
    private Long classId;
    private Long teacherId;
    private String title;
    private String code;
    private LocalDateTime createdAt;
    private ClassStatus status;
    private int studentCount;

    public ClassResponseDto(Class class_) {
        this.classId = class_.getId();
        this.teacherId = class_.getTeacher().getId();
        this.title = class_.getTitle();
        this.code = class_.getCode();
        this.createdAt = class_.getCreatedAt();
        this.status = class_.getStatus();
        this.studentCount = class_.getStudentCount();
    }
}
