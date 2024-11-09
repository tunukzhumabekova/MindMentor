package com.mindmentor.repository;

import com.example.public_.tables.records.ServiceRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.public_.tables.Service.SERVICE;

@Repository
public class ServiceRepository {
    private final DSLContext dslContext;

    public ServiceRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int saveService(String name, String description, int usersId) {
        return Objects.requireNonNull(dslContext.insertInto(SERVICE, SERVICE.NAME, SERVICE.DESCRIPTION, SERVICE.USERS_ID)
                        .values(name, description, usersId)
                        .returning(SERVICE.ID)
                        .fetchOne())
                .getId();
    }

    public ServiceRecord getServiceById(int serviceId) {
        return dslContext.selectFrom(SERVICE)
                .where(SERVICE.ID.eq(serviceId))
                .fetchOne();
    }

    public List<ServiceRecord> getAllServices() {
        return dslContext.selectFrom(SERVICE)
                .fetch();
    }

    public int updateService(int serviceId, String name, String description, int usersId) {
        return dslContext.update(SERVICE)
                .set(SERVICE.NAME, name)
                .set(SERVICE.DESCRIPTION, description)
                .set(SERVICE.USERS_ID, usersId)
                .where(SERVICE.ID.eq(serviceId))
                .execute();
    }

    public int deleteService(int serviceId) {
        return dslContext.deleteFrom(SERVICE)
                .where(SERVICE.ID.eq(serviceId))
                .execute();
    }
}