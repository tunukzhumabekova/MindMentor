package com.mindmentor.repository;

import com.example.public_.tables.records.PriceOfServiceRecord;
import com.mindmentor.model.response.PriceOfServiceResponse;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.public_.tables.PriceOfService.PRICE_OF_SERVICE;

@Repository
public class PriceOfServiceRepository {
    private final DSLContext dslContext;

    public PriceOfServiceRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void savePriceOfService(double price, int mentorId, int serviceId) {
        Objects.requireNonNull(dslContext.insertInto(PRICE_OF_SERVICE, PRICE_OF_SERVICE.PRICE,
                                PRICE_OF_SERVICE.MENTOR_ID, PRICE_OF_SERVICE.SERVICE_ID)
                        .values(price, mentorId, serviceId)
                        .returning(PRICE_OF_SERVICE.ID)
                        .fetchOne())
                .getId();
    }

    public PriceOfServiceResponse getPriceOfServiceById(int priceOfServiceId) {
        PriceOfServiceRecord record = dslContext.selectFrom(PRICE_OF_SERVICE)
                .where(PRICE_OF_SERVICE.ID.eq(priceOfServiceId))
                .fetchOne();

        assert record != null;

        return new PriceOfServiceResponse(
                record.getId(),
                record.getPrice(),
                record.getMentorId(),
                record.getServiceId()
        );
    }

    public List<PriceOfServiceResponse> getAllPricesOfService() {
        List<PriceOfServiceRecord> records = dslContext.selectFrom(PRICE_OF_SERVICE)
                .fetchInto(PriceOfServiceRecord.class);

        List<PriceOfServiceResponse> responses = new ArrayList<>();
        for (PriceOfServiceRecord record : records) {
            responses.add(new PriceOfServiceResponse(
                    record.getId(),
                    record.getPrice(),
                    record.getMentorId(),
                    record.getServiceId()
            ));
        }
        return responses;
    }

    public void updatePriceOfService(int priceOfServiceId, double price, int mentorId, int serviceId) {
        dslContext.update(PRICE_OF_SERVICE)
                .set(PRICE_OF_SERVICE.PRICE, price)
                .set(PRICE_OF_SERVICE.MENTOR_ID, mentorId)
                .set(PRICE_OF_SERVICE.SERVICE_ID, serviceId)
                .where(PRICE_OF_SERVICE.ID.eq(priceOfServiceId))
                .execute();
    }

    public void deletePriceOfService(int priceOfServiceId) {
        dslContext.deleteFrom(PRICE_OF_SERVICE)
                .where(PRICE_OF_SERVICE.ID.eq(priceOfServiceId))
                .execute();
    }
}