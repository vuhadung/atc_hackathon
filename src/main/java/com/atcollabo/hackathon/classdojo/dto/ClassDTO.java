package com.atcollabo.hackathon.classdojo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ClassDTO {
    @NotEmpty(message = "class title is required.")
    private String title;


}
