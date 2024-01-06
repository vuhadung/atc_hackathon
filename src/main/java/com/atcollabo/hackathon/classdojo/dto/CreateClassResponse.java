package com.atcollabo.hackathon.classdojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CreateClassResponse {
    private Long classId;
    private Long teacherId;
    private String title;
    private String code;
    private String createdAt;
    private String status;
    private Integer studentCount;
}
