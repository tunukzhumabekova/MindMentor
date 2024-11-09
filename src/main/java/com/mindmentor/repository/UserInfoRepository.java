package com.mindmentor.repository;

import com.example.public_.tables.records.UsersInfoRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.public_.Tables.USERS_INFO;

@Repository
public class UserInfoRepository {
    private final DSLContext dslContext;

    public UserInfoRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int save(UsersInfoRecord usersInfoRecord) {
        return Objects.requireNonNull(dslContext.insertInto(USERS_INFO)
                        .set(usersInfoRecord)
                        .returningResult(USERS_INFO.ID)
                        .fetchOne())
                .getValue(USERS_INFO.ID);
    }

    public UsersInfoRecord findByEmail(String email) {
        return dslContext.selectFrom(USERS_INFO)
                .where(USERS_INFO.EMAIL.eq(email))
                .fetchOne();
    }

    public boolean existsByEmail(String email) {
        return dslContext.fetchExists(
                dslContext.selectFrom(USERS_INFO)
                        .where(USERS_INFO.EMAIL.eq(email))
        );
    }
}