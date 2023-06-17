package com.drcassessmenttask.drcassessmenttask.services;

import com.drcassessmenttask.drcassessmenttask.payloads.TeacherDto;

public interface TeacherServices {

    TeacherDto signUpTeacher(TeacherDto teacherDto);

    TeacherDto loginTeacher(String email , String password);

}
