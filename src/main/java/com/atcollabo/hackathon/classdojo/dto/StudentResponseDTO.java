package com.atcollabo.hackathon.classdojo.dto;

import com.atcollabo.hackathon.classdojo.entity.ClassStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudentResponseDTO {
    private Long classId;
    private Long teacherId;
    private String title;
    private String teacherFullName;
    private String code;
    private ClassStatus status;

}
