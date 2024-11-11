package com.mindmentor.repository;

import com.example.public_.tables.records.PriceOfServiceRecord;
import com.example.public_.tables.records.ServiceRecord;
import com.mindmentor.model.request.ServiceCreateRequest;
import com.mindmentor.model.response.ServiceResponse;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.public_.tables.PriceOfService.PRICE_OF_SERVICE;
import static com.example.public_.tables.Service.SERVICE;

@Repository
public class ServiceRepository {
    private final DSLContext dslContext;

    public ServiceRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void createService(ServiceCreateRequest request) {
        Integer serviceId = dslContext.select(SERVICE.ID)
                .from(SERVICE)
                .where(SERVICE.NAME.eq(request.service_name()))
                .fetchOne(SERVICE.ID);

        if (serviceId == null) {
            serviceId = dslContext.insertInto(SERVICE,
                    SERVICE.NAME,
                    SERVICE.DESCRIPTION,
                    SERVICE.USERS_ID)
                    .values(request.service_name(),
                            request.service_description(),
                            request.userInfoId())
                    .execute();
        }

        dslContext.insertInto(PRICE_OF_SERVICE,
                PRICE_OF_SERVICE.PRICE,
                PRICE_OF_SERVICE.SERVICE_ID,
                PRICE_OF_SERVICE.MENTOR_ID)
                .values(request.price(),
                        serviceId,
                        request.mentorId())
                .execute();
    }

    public ServiceResponse getServiceById(int serviceId) {
        ServiceRecord serviceRecord = dslContext.selectFrom(SERVICE)
                .where(SERVICE.ID.eq(serviceId))
                .fetchOne();

        assert serviceRecord != null;

        PriceOfServiceRecord priceRecord = dslContext.selectFrom(PRICE_OF_SERVICE)
                .where(PRICE_OF_SERVICE.SERVICE_ID.eq(serviceId))
                .fetchOne();

        double price = (priceRecord != null) ? priceRecord.getPrice() : 0;

        return new ServiceResponse(
                serviceRecord.getId(),
                serviceRecord.getName(),
                serviceRecord.getDescription()
                /*price*/
        );
    }

    public List<ServiceResponse> getAllServices() {
        List<ServiceRecord> serviceRecords = dslContext.selectFrom(SERVICE)
                .fetchInto(ServiceRecord.class);

        List<ServiceResponse> responses = new ArrayList<>();
        for (ServiceRecord serviceRecord : serviceRecords) {
            PriceOfServiceRecord priceRecord = dslContext.selectFrom(PRICE_OF_SERVICE)
                    .where(PRICE_OF_SERVICE.SERVICE_ID.eq(serviceRecord.getId()))
                    .fetchOne();

            double price = (priceRecord != null) ? priceRecord.getPrice() : 0;

            responses.add(new ServiceResponse(
                    serviceRecord.getId(),
                    serviceRecord.getName(),
                    serviceRecord.getDescription(),
                    price
            ));
        }
        return responses;
    }

    public void updateService(int serviceId, String name, String description, int usersId) {
        dslContext.update(SERVICE)
                .set(SERVICE.NAME, name)
                .set(SERVICE.DESCRIPTION, description)
                .set(SERVICE.USERS_ID, usersId)
                .where(SERVICE.ID.eq(serviceId))
                .execute();
    }

    public void deleteService(int serviceId) {
        dslContext.deleteFrom(SERVICE)
                .where(SERVICE.ID.eq(serviceId))
                .execute();
    }
}