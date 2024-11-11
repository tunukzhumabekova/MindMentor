package com.mindmentor.controller;

import com.mindmentor.model.request.CourseCreateRequest;
import com.mindmentor.model.response.CourseResponse;
import com.mindmentor.service.CoursesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
@Tag(name = "Course API", description = "Endpoints for managing courses")
public class CourseApi {
    private final CoursesService coursesService;

    @PostMapping("/")
    @Operation(summary = "Save Course", description = "Creates a new course.")
    public void saveCourse(@Valid @RequestBody CourseCreateRequest request) {
        coursesService.saveCourse(request);
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Get Course by ID", description = "Fetches course details by ID.")
    public CourseResponse getCourseById(@PathVariable int courseId) {
        return coursesService.getCourseById(courseId);
    }

    @GetMapping("/")
    @Operation(summary = "Get All Courses", description = "Fetches all courses.")
    public List<CourseResponse> getAllCourses() {
        return coursesService.getAllCourse();
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update Course", description = "Updates an existing course.")
    public int updateCourse(@PathVariable int courseId, @Valid @RequestBody CourseCreateRequest request) {
        return coursesService.updateCourse(courseId, request);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Delete Course", description = "Deletes a course by ID.")
    public int deleteCourse(@PathVariable int courseId) {
        return coursesService.deleteCourse(courseId);
    }
}