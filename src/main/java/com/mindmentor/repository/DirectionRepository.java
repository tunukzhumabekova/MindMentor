package com.mindmentor.repository;

import com.example.public_.tables.records.DirectionRecord;
import com.mindmentor.model.response.DirectionResponse;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.public_.tables.Direction.DIRECTION;

@Repository
public class DirectionRepository {
    private final DSLContext dslContext;

    public DirectionRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int createDirection(String name) {
        return Objects.requireNonNull(dslContext.insertInto(DIRECTION, DIRECTION.NAME)
                        .values(name)
                        .returning(DIRECTION.ID)
                        .fetchOne())
                .getId();
    }

    public DirectionResponse getDirectionById(int directionId) {
        DirectionRecord directionRecord = dslContext.selectFrom(DIRECTION)
                .where(DIRECTION.ID.eq(directionId))
                .fetchOne();

        assert directionRecord != null;

        return new DirectionResponse(
                directionRecord.getId(),
                directionRecord.getName()
        );
    }

    public List<DirectionResponse> getAllDirections() {
        List<DirectionRecord> directionRecords = dslContext.selectFrom(DIRECTION)
                .fetch();

        List<DirectionResponse> directionResponses = new ArrayList<>();

        for (DirectionRecord directionRecord : directionRecords) {
            DirectionResponse directionResponse = new DirectionResponse(
                    directionRecord.getId(),
                    directionRecord.getName()
            );

            directionResponses.add(directionResponse);
        }

        return directionResponses;
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