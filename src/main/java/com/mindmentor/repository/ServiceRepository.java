package com.mindmentor.repository;

import com.example.public_.tables.records.ServiceRecord;
import com.mindmentor.model.request.ServiceCreateRequest;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

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

    public ServiceRecord getServiceById(Integer serviceId) {
        return dslContext.selectFrom(SERVICE)
                .where(SERVICE.ID.eq(serviceId))
                .fetchOne();
    }

    public List<ServiceRecord> getAllServices() {
        return dslContext.selectFrom(SERVICE)
                .fetch();
    }

    public void updateService(Integer serviceId, String name, String description, Integer usersId) {
        dslContext.update(SERVICE)
                .set(SERVICE.NAME, name)
                .set(SERVICE.DESCRIPTION, description)
                .set(SERVICE.USERS_ID, usersId)
                .where(SERVICE.ID.eq(serviceId))
                .execute();
    }

    public void deleteService(Integer serviceId) {
        dslContext.deleteFrom(SERVICE)
                .where(SERVICE.ID.eq(serviceId))
                .execute();
    }
}