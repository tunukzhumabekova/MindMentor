package com.mindmentor.repository;

import com.example.public_.tables.records.MentorInfoRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.public_.tables.MentorInfo.MENTOR_INFO;

@Repository
public class MentorInfoRepository {
    private final DSLContext dslContext;

    public MentorInfoRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int saveMentorInfo(int directionId, String language, int experience, String aboutMentor, String videoUrl, int usersId) {
        return dslContext.insertInto(MENTOR_INFO, MENTOR_INFO.DIRECTION_ID, MENTOR_INFO.LANGUAGE, MENTOR_INFO.EXPERIENCE,
                        MENTOR_INFO.ABOUT_MENTOR, MENTOR_INFO.VIDEO_URL, MENTOR_INFO.USERS_ID)
                .values(directionId, language, experience, aboutMentor, videoUrl, usersId)
                .returning(MENTOR_INFO.ID)
                .fetchOne()
                .getId();
    }

    public MentorInfoRecord getMentorInfoById(int mentorInfoId) {
        return dslContext.selectFrom(MENTOR_INFO)
                .where(MENTOR_INFO.ID.eq(mentorInfoId))
                .fetchOne();
    }

    public List<MentorInfoRecord> getAllMentorInfo() {
        return dslContext.selectFrom(MENTOR_INFO)
                .fetch();
    }

    public int updateMentorInfo(int mentorInfoId, int directionId, String language, int experience, String aboutMentor, String videoUrl) {
        return dslContext.update(MENTOR_INFO)
                .set(MENTOR_INFO.DIRECTION_ID, directionId)
                .set(MENTOR_INFO.LANGUAGE, language)
                .set(MENTOR_INFO.EXPERIENCE, experience)
                .set(MENTOR_INFO.ABOUT_MENTOR, aboutMentor)
                .set(MENTOR_INFO.VIDEO_URL, videoUrl)
                .where(MENTOR_INFO.ID.eq(mentorInfoId))
                .execute();
    }

    public int deleteMentorInfo(int mentorInfoId) {
        return dslContext.deleteFrom(MENTOR_INFO)
                .where(MENTOR_INFO.ID.eq(mentorInfoId))
                .execute();
    }
}