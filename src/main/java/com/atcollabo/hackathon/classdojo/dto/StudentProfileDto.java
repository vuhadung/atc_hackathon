package com.atcollabo.hackathon.classdojo.dto;

import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class StudentProfileDto {
    private Long studentId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<StudentAttendanceRateByClassDto> classes;
    private double avgAttendanceRate;


    @Override
    public String toString() {
        return "StudentProfileDto{" +
                "studentId=" + studentId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", classes=" + classes +
                ", avgAttendanceRate=" + avgAttendanceRate +
                '}';
    }
}
