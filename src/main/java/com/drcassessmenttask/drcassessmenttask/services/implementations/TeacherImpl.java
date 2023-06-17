package com.drcassessmenttask.drcassessmenttask.services.implementations;

import com.drcassessmenttask.drcassessmenttask.entittes.Teacher;
import com.drcassessmenttask.drcassessmenttask.payloads.TeacherDto;
import com.drcassessmenttask.drcassessmenttask.repositories.TeacherRepo;
import com.drcassessmenttask.drcassessmenttask.services.TeacherServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeacherImpl implements TeacherServices {

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public TeacherDto signUpTeacher(TeacherDto teacherDto) {

        Teacher teacher = this.modelMapper.map(teacherDto, Teacher.class);
        Teacher byEmail = teacherRepo.findByEmail(teacherDto.getEmail());
        if (byEmail != null) {
            throw new IllegalArgumentException("Teacher with this Email is already registered");
        }

       String encryptedPassword = passwordEncoder.encode(teacherDto.getPassword());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPassword(encryptedPassword);
        teacher.setName(teacherDto.getName());
        teacher.setUserName(teacherDto.getUserName());
        teacher.setGender(teacherDto.getGender());
        teacher.setAge(teacherDto.getAge());

        Teacher savedTeacher = teacherRepo.save(teacher);
        return this.modelMapper.map(savedTeacher,TeacherDto.class);
    }

    @Override
    public TeacherDto loginTeacher(String email, String password) {

        Teacher teacher = teacherRepo.findByEmail(email);

        if (teacher == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (!passwordEncoder.matches(password, teacher.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return this.modelMapper.map(teacher,TeacherDto.class);


    }


}
