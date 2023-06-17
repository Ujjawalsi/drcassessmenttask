package com.drcassessmenttask.drcassessmenttask.controllers;

import com.drcassessmenttask.drcassessmenttask.payloads.LoginRequest;
import com.drcassessmenttask.drcassessmenttask.payloads.TeacherDto;
import com.drcassessmenttask.drcassessmenttask.services.TeacherServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherServices teacherServices;

    @PostMapping("/signup")
    public ResponseEntity<TeacherDto> createSatResult(@Valid @RequestBody TeacherDto teacherDto){
        TeacherDto resultDto = this.teacherServices.signUpTeacher(teacherDto);
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TeacherDto>  loginTeacher(@RequestBody LoginRequest loginRequest) {
        TeacherDto teacherDto = teacherServices.loginTeacher(loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }

}
