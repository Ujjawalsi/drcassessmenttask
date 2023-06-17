package com.drcassessmenttask.drcassessmenttask.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    @NotEmpty(message = "Name never be empty")
    @NotNull
    private String name;
    @NotEmpty(message = "Roll No never be empty")
    @NotNull
    private String rollNo;
    @NotEmpty(message = "Department never be empty")
    @NotNull
    private String department;
    @NotEmpty(message = "Standard never be empty")
    @NotNull
    private String standard;

    private String gender;

    private int age;
}
