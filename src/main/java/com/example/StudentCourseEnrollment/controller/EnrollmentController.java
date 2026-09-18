package com.example.StudentCourseEnrollment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.StudentCourseEnrollment.entity.Enrollment;
import com.example.StudentCourseEnrollment.repository.EnrollmentRepository;
import com.example.StudentCourseEnrollment.repository.StudentRepository;
import com.example.StudentCourseEnrollment.repository.CourseRepository;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<String> createEnrollment(
            @RequestBody Enrollment enrollment) {

        if (!studentRepository.existsById(enrollment.getStudentId())) {
            return ResponseEntity.ok("Student not found");
        }

        if (!courseRepository.existsById(enrollment.getCourseId())) {
            return ResponseEntity.ok("Course not found");
        }

        if (enrollmentRepository.existsByStudentIdAndCourseId(
                enrollment.getStudentId(),
                enrollment.getCourseId())) {

            return ResponseEntity.ok("Student already enrolled");
        }

        enrollmentRepository.save(enrollment);

        return ResponseEntity.ok("Enrollment created successfully");
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnrollmentById(@PathVariable Long id) {

        return enrollmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(
            @PathVariable Long id,
            @RequestBody Enrollment enrollment) {

        return enrollmentRepository.findById(id)
                .map(existingEnrollment -> {

                    existingEnrollment.setStudentId(enrollment.getStudentId());
                    existingEnrollment.setCourseId(enrollment.getCourseId());
                    existingEnrollment.setEnrollmentDate(
                            enrollment.getEnrollmentDate());
                    existingEnrollment.setStatus(enrollment.getStatus());

                    enrollmentRepository.save(existingEnrollment);

                    return ResponseEntity.ok(existingEnrollment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(
            @PathVariable Long id) {

        if (!enrollmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        enrollmentRepository.deleteById(id);

        return ResponseEntity.ok("Enrollment deleted successfully");
    }
}