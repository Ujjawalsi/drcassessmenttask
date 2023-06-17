package com.drcassessmenttask.drcassessmenttask.services.implementations;

import com.drcassessmenttask.drcassessmenttask.entittes.Student;
import com.drcassessmenttask.drcassessmenttask.exceptions.ResourceNotFoundException;
import com.drcassessmenttask.drcassessmenttask.payloads.GetAllResponse;
import com.drcassessmenttask.drcassessmenttask.payloads.StudentDto;
import com.drcassessmenttask.drcassessmenttask.repositories.StudentRepo;
import com.drcassessmenttask.drcassessmenttask.services.StudentServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentImpl implements StudentServices {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentRepo studentRepo;
    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        Student student = this.modelMapper.map(studentDto, Student.class);
        Student byRollNo = studentRepo.findByRollNo(studentDto.getRollNo());
        if (byRollNo != null) {
             throw new IllegalArgumentException("Student with this Roll Number is already registered");
        }
        student.setName(studentDto.getName());
        student.setRollNo(studentDto.getRollNo());
        student.setDepartment(studentDto.getDepartment());
        student.setStandard(studentDto.getStandard());
        student.setAge(studentDto.getAge());
        student.setGender(studentDto.getGender());
        Student savedStudent = studentRepo.save(student);
        return this.modelMapper.map(savedStudent, StudentDto.class);

    }

    @Override
    public StudentDto editStudent(Long id, StudentDto studentDto) {
        Student existingStudent = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student","Id", id));
        existingStudent.setRollNo(studentDto.getRollNo());
        existingStudent.setName(studentDto.getName());
        existingStudent.setRollNo(studentDto.getRollNo());
        existingStudent.setDepartment(studentDto.getDepartment());
        existingStudent.setStandard(studentDto.getStandard());
        existingStudent.setGender(studentDto.getGender());
        existingStudent.setAge(studentDto.getAge());

        Student updatedStudent = studentRepo.save(existingStudent);
        return this.modelMapper.map(updatedStudent,StudentDto.class);
    }

    @Override
    public StudentDto getStudent(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student","Id", id));
        return this.modelMapper.map(student,StudentDto.class);
    }

    @Override
    public GetAllResponse getAllStudentWithPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort =(sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<Student> studentList = studentRepo.findAll(pageable);
        List<Student> content = studentList.getContent();
        List<StudentDto> studentDtoList = content.stream().map((student -> this.modelMapper.map(student, StudentDto.class))).collect(Collectors.toList());
        GetAllResponse getAllResponse = new GetAllResponse();
        getAllResponse.setContent(studentDtoList);
        getAllResponse.setPageNumber(studentList.getNumber());
        getAllResponse.setPageSize(studentList.getSize());
        getAllResponse.setTotalElements(studentList.getTotalElements());
        getAllResponse.setTotalPages(studentList.getTotalPages());
        getAllResponse.setLastPage(studentList.isLast());

        return  getAllResponse;
    }
}
