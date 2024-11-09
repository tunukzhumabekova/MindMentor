package com.mindmentor.repository;

import com.example.public_.tables.records.DirectionRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.public_.tables.Direction.DIRECTION;

@Repository
public class DirectionRepository {
    private final DSLContext dslContext;

    public DirectionRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int createDirection(String name) {
        return dslContext.insertInto(DIRECTION, DIRECTION.NAME)
                .values(name)
                .returning(DIRECTION.ID)
                .fetchOne()
                .getId();
    }

    public DirectionRecord getDirectionById(int directionId) {
        return dslContext.selectFrom(DIRECTION)
                .where(DIRECTION.ID.eq(directionId))
                .fetchOne();
    }

    public List<DirectionRecord> getAllDirections() {
        return dslContext.selectFrom(DIRECTION)
                .fetch();
    }

    public int updateDirection(int directionId, String name) {
        return dslContext.update(DIRECTION)
                .set(DIRECTION.NAME, name)
                .where(DIRECTION.ID.eq(directionId))
                .execute();
    }

    public int deleteDirection(int directionId) {
        return dslContext.deleteFrom(DIRECTION)
                .where(DIRECTION.ID.eq(directionId))
                .execute();
    }
}