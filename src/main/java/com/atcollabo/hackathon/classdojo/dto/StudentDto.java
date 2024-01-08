package com.atcollabo.hackathon.classdojo.dto;


import com.atcollabo.hackathon.classdojo.entity.StudentClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private Long studentId;
    private Long classId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private int attendance;

    private float attendanceRate;

    public StudentDto(StudentClass studentClass) {
        this.studentId = studentClass.getStudent().getId();
        this.classId = studentClass.get_class().getId();
        this.fullName = studentClass.getStudent().getFullName();
        this.email = studentClass.getStudent().getEmail();
        this.phoneNumber = studentClass.getStudent().getPhone_number();
        this.attendance = studentClass.getAttendance();
    }
}
