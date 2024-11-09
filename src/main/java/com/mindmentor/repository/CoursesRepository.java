package com.mindmentor.repository;

import com.example.public_.tables.records.CoursesRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.public_.tables.Courses.COURSES;

@Repository
public class CoursesRepository {
    private final DSLContext dslContext;

    public CoursesRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int saveCourse(Integer userId, Integer mentorId, Integer directionId) {
        return Objects.requireNonNull(dslContext.insertInto(COURSES, COURSES.USERS_ID,
                                COURSES.MENTOR_INFO_ID, COURSES.DIRECTION_ID)
                        .values(userId, mentorId, directionId)
                        .returning(COURSES.ID)
                        .fetchOne())
                .getId();
    }

    public CoursesRecord getCourseById(int courseId) {
        return dslContext.selectFrom(COURSES)
                .where(COURSES.ID.eq(courseId))
                .fetchOne();
    }

    public int updateCourse(int courseId, Integer userId, Integer mentorInfoId, Integer directionId) {
        return dslContext.update(COURSES)
                .set(COURSES.USERS_ID, userId)
                .set(COURSES.MENTOR_INFO_ID, mentorInfoId)
                .set(COURSES.DIRECTION_ID, directionId)
                .where(COURSES.ID.eq(courseId))
                .execute();
    }

    public int deleteCourse(int courseId) {
        return dslContext.deleteFrom(COURSES)
                .where(COURSES.ID.eq(courseId))
                .execute();
    }
}