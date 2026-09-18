package com.example.StudentCourseEnrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudentCourseEnrollment.entity.Marks;

public interface MarksRepository extends JpaRepository<Marks, Long> {

}