package com.atcollabo.hackathon.classdojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckAttendaceDto {
    private Long classId;
    private List<Long> presentStudentIds;
}
