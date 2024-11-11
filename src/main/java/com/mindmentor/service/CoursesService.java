package com.mindmentor.service;

import com.mindmentor.model.request.CourseCreateRequest;
import com.mindmentor.model.response.CourseResponse;

import java.util.List;

public interface CoursesService {
    void saveCourse(CourseCreateRequest request);
    CourseResponse getCourseById(int courseId);
    List<CourseResponse> getAllCourse();
    int updateCourse(int courseId, CourseCreateRequest request);
    int deleteCourse(int courseId);
}