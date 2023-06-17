package com.drcassessmenttask.drcassessmenttask.services;

import com.drcassessmenttask.drcassessmenttask.payloads.GetAllResponse;
import com.drcassessmenttask.drcassessmenttask.payloads.StudentDto;

import java.util.List;

public interface StudentServices {

    StudentDto addStudent(StudentDto studentDto);

    StudentDto editStudent(Long id , StudentDto studentDto);

    StudentDto getStudent(Long id);

    GetAllResponse getAllStudentWithPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
