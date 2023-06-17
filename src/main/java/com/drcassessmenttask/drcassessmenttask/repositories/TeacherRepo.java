package com.drcassessmenttask.drcassessmenttask.repositories;

import com.drcassessmenttask.drcassessmenttask.entittes.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);
}
