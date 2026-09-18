package com.example.StudentCourseEnrollment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.StudentCourseEnrollment.entity.Marks;
import com.example.StudentCourseEnrollment.repository.MarksRepository;
import com.example.StudentCourseEnrollment.repository.StudentRepository;
import com.example.StudentCourseEnrollment.repository.CourseRepository;

@RestController
@RequestMapping("/marks")
public class MarksController {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<String> createMarks(@RequestBody Marks marks) {

        if (!studentRepository.existsById(marks.getStudentId())) {
            return ResponseEntity.ok("Student not found");
        }

        if (!courseRepository.existsById(marks.getCourseId())) {
            return ResponseEntity.ok("Course not found");
        }

        if (marks.getMarks() < 0) {
            return ResponseEntity.ok("Marks cannot be negative");
        }

        if (marks.getMarks() > marks.getTotalMarks()) {
            return ResponseEntity.ok("Marks cannot be greater than total marks");
        }

        marksRepository.save(marks);

        return ResponseEntity.ok("Marks created successfully");
    }

    @GetMapping
    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMarksById(@PathVariable Long id) {

        return marksRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMarks(
            @PathVariable Long id,
            @RequestBody Marks marks) {

        return marksRepository.findById(id)
                .map(existingMarks -> {

                    existingMarks.setStudentId(marks.getStudentId());
                    existingMarks.setCourseId(marks.getCourseId());
                    existingMarks.setExamName(marks.getExamName());
                    existingMarks.setMarks(marks.getMarks());
                    existingMarks.setTotalMarks(marks.getTotalMarks());

                    marksRepository.save(existingMarks);

                    return ResponseEntity.ok(existingMarks);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarks(@PathVariable Long id) {

        if (!marksRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        marksRepository.deleteById(id);

        return ResponseEntity.ok("Marks deleted successfully");
    }
}