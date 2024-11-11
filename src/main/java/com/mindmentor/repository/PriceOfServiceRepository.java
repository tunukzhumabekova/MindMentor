package com.mindmentor.repository;

import com.example.public_.tables.records.PriceOfServiceRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.public_.tables.PriceOfService.PRICE_OF_SERVICE;

@Repository
public class PriceOfServiceRepository {
    private final DSLContext dslContext;

    public PriceOfServiceRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Integer savePriceOfService(Double price, Integer mentorId, Integer serviceId) {
        return Objects.requireNonNull(dslContext.insertInto(PRICE_OF_SERVICE, PRICE_OF_SERVICE.PRICE,
                                PRICE_OF_SERVICE.MENTOR_ID, PRICE_OF_SERVICE.SERVICE_ID)
                        .values(price, mentorId, serviceId)
                        .returning(PRICE_OF_SERVICE.ID)
                        .fetchOne())
                .getId();
    }

    public PriceOfServiceRecord getPriceOfServiceById(Integer priceOfServiceId) {
        return dslContext.selectFrom(PRICE_OF_SERVICE)
                .where(PRICE_OF_SERVICE.ID.eq(priceOfServiceId))
                .fetchOne();
    }

    public List<PriceOfServiceRecord> getAllPricesOfService() {
        return dslContext.selectFrom(PRICE_OF_SERVICE)
                .fetch();
    }

    public Integer updatePriceOfService(Integer priceOfServiceId, Double price, Integer mentorId, Integer serviceId) {
        return dslContext.update(PRICE_OF_SERVICE)
                .set(PRICE_OF_SERVICE.PRICE, price)
                .set(PRICE_OF_SERVICE.MENTOR_ID, mentorId)
                .set(PRICE_OF_SERVICE.SERVICE_ID, serviceId)
                .where(PRICE_OF_SERVICE.ID.eq(priceOfServiceId))
                .execute();
    }

    public Integer deletePriceOfService(Integer priceOfServiceId) {
        return dslContext.deleteFrom(PRICE_OF_SERVICE)
                .where(PRICE_OF_SERVICE.ID.eq(priceOfServiceId))
                .execute();
    }
}