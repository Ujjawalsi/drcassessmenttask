package com.drcassessmenttask.drcassessmenttask.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {
    private Long id ;
    @NotEmpty(message = "Name never be empty")
    private String name;
    @NotEmpty(message = "UserName never be empty")
    private String userName;
    @NotEmpty(message = "Name never be empty")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid Email")
    private String email;

    private String password;

    private String gender;

    private int age;

}
