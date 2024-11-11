package com.mindmentor.service.impl;

import com.mindmentor.model.request.CourseCreateRequest;
import com.mindmentor.model.response.CourseResponse;
import com.mindmentor.repository.CoursesRepository;
import com.mindmentor.service.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CoursesService {
    private final CoursesRepository coursesRepository;

    @Override
    public void saveCourse(CourseCreateRequest request) {
        coursesRepository.saveCourse(request);
    }

    @Override
    public CourseResponse getCourseById(int courseId) {
        return coursesRepository.getCourseById(courseId);
    }

    @Override
    public List<CourseResponse> getAllCourse() {
        return coursesRepository.getAllCourse();
    }

    @Override
    public int updateCourse(int courseId, CourseCreateRequest request) {
        return coursesRepository.updateCourse(courseId, request);
    }

    @Override
    public int deleteCourse(int courseId) {
        return coursesRepository.deleteCourse(courseId);
    }
}