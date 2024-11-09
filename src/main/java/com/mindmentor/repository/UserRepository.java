package com.mindmentor.repository;

import com.example.public_.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.public_.tables.Users.USERS;

@Repository
public class UserRepository {
    private final DSLContext dslContext;

    public UserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public int save(UsersRecord usersRecord, int userId) {
        return Objects.requireNonNull(dslContext.insertInto(USERS)
                        .set(usersRecord)
                        .set(USERS.USERS_INFO_ID, userId)
                        .returningResult(USERS.ID)
                        .fetchOne())
                .getValue(USERS.ID);
    }

    public UsersRecord findUserByUserInfoId(Integer userInfoId) {
        return dslContext.selectFrom(USERS)
                .where(USERS.USERS_INFO_ID.eq(userInfoId))
                .fetchOne();
    }
}