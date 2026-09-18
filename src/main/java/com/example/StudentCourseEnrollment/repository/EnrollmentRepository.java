package com.example.StudentCourseEnrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudentCourseEnrollment.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}