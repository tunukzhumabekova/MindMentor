package com.mindmentor.repository;

import com.example.public_.tables.records.CoursesRecord;
import com.mindmentor.exceptions.NotFoundException;
import com.mindmentor.model.request.CourseCreateRequest;
import com.mindmentor.model.response.CourseResponse;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.public_.Tables.MENTOR_INFO;
import static com.example.public_.Tables.USERS;
import static com.example.public_.tables.Courses.COURSES;

@Repository
public class CoursesRepository {
    private final DSLContext dslContext;

    public CoursesRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void saveCourse(CourseCreateRequest request) {
        CoursesRecord record = dslContext.insertInto(COURSES,
                        COURSES.COURSE_NAME,
                        COURSES.DESCRIPTION,
                        COURSES.WHAT_YOU_WILL_LEARN,
                        COURSES.LANGUAGE,
                        COURSES.MENTOR_INFO_ID,
                        COURSES.DIRECTION_ID)
                .values(
                        request.course_name(),
                        request.course_description(),
                        request.what_you_will_learn(),
                        request.language(),
                        request.mentorId(),
                        request.directionId()
                )
                .returning(COURSES.ID)
                .fetchOne();

        if (record == null) {
            throw new IllegalStateException("Failed to insert course");
        }

        record.getId();
    }

    public CourseResponse getCourseById(int courseId) {
        return dslContext.select(
                        COURSES.COURSE_NAME,
                        COURSES.DESCRIPTION,
                        COURSES.FILE_URL,
                        COURSES.PRICE,
                        USERS.FIO,
                        USERS.IMAGE,
                        COURSES.LAST_UPDATED,
                        COURSES.WHAT_YOU_WILL_LEARN)
                .from(COURSES)
                .join(MENTOR_INFO).on(COURSES.MENTOR_INFO_ID.eq(MENTOR_INFO.ID))
                .join(USERS).on(MENTOR_INFO.USERS_ID.eq(USERS.ID))
                .where(COURSES.ID.eq(courseId))
                .fetchOptional()
                .map(record -> new CourseResponse(
                        record.get(COURSES.COURSE_NAME),
                        record.get(COURSES.DESCRIPTION),
                        record.get(COURSES.FILE_URL),
                        record.get(COURSES.PRICE),
                        record.get(USERS.FIO),
                        record.get(USERS.IMAGE),
                        record.get(COURSES.LAST_UPDATED),
                        record.get(COURSES.WHAT_YOU_WILL_LEARN)
                ))
                .orElseThrow(() -> new NotFoundException("Course with ID " + courseId + " not found."));
    }

    public List<CourseResponse> getAllCourse() {
        return dslContext.select(
                COURSES.COURSE_NAME,
                COURSES.DESCRIPTION,
                COURSES.FILE_URL,
                COURSES.PRICE,
                USERS.FIO,
                USERS.IMAGE)
                .from(COURSES)
                .join(MENTOR_INFO).on(COURSES.MENTOR_INFO_ID.eq(MENTOR_INFO.ID))
                .join(USERS).on(MENTOR_INFO.USERS_ID.eq(USERS.ID))
                .fetchStream()
                .map(record -> new CourseResponse(
                        record.get(COURSES.COURSE_NAME),
                        record.get(COURSES.DESCRIPTION),
                        record.get(COURSES.FILE_URL),
                        record.get(COURSES.PRICE),
                        record.get(USERS.FIO),
                        record.get(USERS.IMAGE)
                ))
                .collect(Collectors.toList());
    }

    public int updateCourse(int courseId, CourseCreateRequest request) {
        return dslContext.update(COURSES)
                .set(COURSES.COURSE_NAME, request.course_name())
                .set(COURSES.DESCRIPTION, request.course_description())
                .set(COURSES.WHAT_YOU_WILL_LEARN, request.what_you_will_learn())
                .set(COURSES.LANGUAGE, request.language())
                .set(COURSES.LAST_UPDATED, LocalDateTime.now())
                .set(COURSES.MENTOR_INFO_ID, request.mentorId())
                .set(COURSES.DIRECTION_ID, request.directionId())
                .where(COURSES.ID.eq(courseId))
                .execute();
    }

    public int deleteCourse(int courseId) {
        return dslContext.deleteFrom(COURSES)
                .where(COURSES.ID.eq(courseId))
                .execute();
    }
}