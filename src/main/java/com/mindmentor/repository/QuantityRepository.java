package com.mindmentor.repository;

import com.example.public_.tables.records.QuantityRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.public_.tables.Quantity.QUANTITY;

@Repository
public class QuantityRepository {
    private final DSLContext dslContext;

    public QuantityRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int saveQuantity(int quantityOfMentors, int directionId, String language, int projectId) {
        return Objects.requireNonNull(dslContext.insertInto(QUANTITY, QUANTITY.QUANTITY_OF_MENTORS,
                                QUANTITY.DIRECTION_ID, QUANTITY.LANGUAGE, QUANTITY.PROJECT_ID)
                        .values(quantityOfMentors, directionId, language, projectId)
                        .returning(QUANTITY.ID)
                        .fetchOne())
                .getId();
    }

    public QuantityRecord getQuantityById(int quantityId) {
        return dslContext.selectFrom(QUANTITY)
                .where(QUANTITY.ID.eq(quantityId))
                .fetchOne();
    }

    public List<QuantityRecord> getAllQuantities() {
        return dslContext.selectFrom(QUANTITY)
                .fetch();
    }

    public int updateQuantity(int quantityId, int quantityOfMentors, int directionId, String language, int projectId) {
        return dslContext.update(QUANTITY)
                .set(QUANTITY.QUANTITY_OF_MENTORS, quantityOfMentors)
                .set(QUANTITY.DIRECTION_ID, directionId)
                .set(QUANTITY.LANGUAGE, language)
                .set(QUANTITY.PROJECT_ID, projectId)
                .where(QUANTITY.ID.eq(quantityId))
                .execute();
    }

    public int deleteQuantity(int quantityId) {
        return dslContext.deleteFrom(QUANTITY)
                .where(QUANTITY.ID.eq(quantityId))
                .execute();
    }
}