package com.example.StudentCourseEnrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudentCourseEnrollment.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}