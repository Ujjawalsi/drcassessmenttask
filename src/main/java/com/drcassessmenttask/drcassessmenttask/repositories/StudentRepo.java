package com.drcassessmenttask.drcassessmenttask.repositories;

import com.drcassessmenttask.drcassessmenttask.entittes.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByRollNo(String rollNo);
}
