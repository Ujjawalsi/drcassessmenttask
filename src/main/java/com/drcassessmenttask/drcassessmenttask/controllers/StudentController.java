package com.drcassessmenttask.drcassessmenttask.controllers;

import com.drcassessmenttask.drcassessmenttask.payloads.Constants;
import com.drcassessmenttask.drcassessmenttask.payloads.GetAllResponse;
import com.drcassessmenttask.drcassessmenttask.payloads.StudentDto;
import com.drcassessmenttask.drcassessmenttask.services.StudentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    @PostMapping("/")
    public ResponseEntity<StudentDto> addNewStudent(@Valid @RequestBody StudentDto studentDto){
        StudentDto satResultDto = this.studentServices.addStudent(studentDto);
        return new ResponseEntity<>(satResultDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<StudentDto> updateStudent(@Valid @RequestBody StudentDto studentDto , @PathVariable Long id){

        StudentDto updatedStudent = this.studentServices.editStudent(id, studentDto);
        return  new ResponseEntity<>(updatedStudent,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id){
        StudentDto result = this.studentServices.getStudent(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllResponse> getAllStudent(@RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER, required = false)Integer pageNumber,
                                                        @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false)Integer pageSize,
                                                        @RequestParam(value = "sortBY", defaultValue = Constants.SORT_BY, required = false)String sortBy,
                                                        @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false)String sortDir){
        GetAllResponse allStudentWithPagination = this.studentServices.getAllStudentWithPagination(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allStudentWithPagination,HttpStatus.OK);
    }




}
