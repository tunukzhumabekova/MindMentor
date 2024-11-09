package com.mindmentor.repository;

import com.example.public_.tables.records.FeedbackRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.public_.Tables.FEEDBACK;

@Repository
public class FeedbackRepository {
    private final DSLContext dslContext;

    public FeedbackRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int saveFeedback(double rating, String description, int usersId, int mentorInfoId) {
        return Objects.requireNonNull(dslContext.insertInto(FEEDBACK, FEEDBACK.RATING, FEEDBACK.DESCRIPTION,
                                FEEDBACK.USERS_ID, FEEDBACK.MENTOR_INFO_ID)
                        .values(rating, description, usersId, mentorInfoId)
                        .returning(FEEDBACK.ID)
                        .fetchOne())
                .getId();
    }

    public FeedbackRecord getFeedbackById(int feedbackId) {
        return dslContext.selectFrom(FEEDBACK)
                .where(FEEDBACK.ID.eq(feedbackId))
                .fetchOne();
    }

    public List<FeedbackRecord> getAllFeedbacks() {
        return dslContext.selectFrom(FEEDBACK)
                .fetch();
    }

    public int updateFeedback(int feedbackId, double rating, String description, int usersId, int mentorInfoId) {
        return dslContext.update(FEEDBACK)
                .set(FEEDBACK.RATING, rating)
                .set(FEEDBACK.DESCRIPTION, description)
                .set(FEEDBACK.USERS_ID, usersId)
                .set(FEEDBACK.MENTOR_INFO_ID, mentorInfoId)
                .where(FEEDBACK.ID.eq(feedbackId))
                .execute();
    }

    public int deleteFeedback(int feedbackId) {
        return dslContext.deleteFrom(FEEDBACK)
                .where(FEEDBACK.ID.eq(feedbackId))
                .execute();
    }
}