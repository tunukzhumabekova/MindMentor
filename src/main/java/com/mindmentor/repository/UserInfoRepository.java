package com.mindmentor.repository;

import com.example.public_.enums.Status;
import com.example.public_.tables.records.UsersInfoRecord;
import com.mindmentor.model.request.UserCreateRequest;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static com.example.public_.Tables.USERS;
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

    public void addUser(UserCreateRequest request) {
        Integer userInfoId = Objects.requireNonNull(dslContext
                        .insertInto(USERS_INFO, USERS_INFO.EMAIL, USERS_INFO.PASSWORD)
                        .values(request.email(), request.password())
                        .returning(USERS_INFO.ID)
                        .fetchOne())
                .getId();

        dslContext.insertInto(USERS, USERS.FIO, USERS.PHONE_NUMBER, USERS.USERS_INFO_ID)
                .values(request.fio(), request.phone_number(), userInfoId)
                .execute();
    }

    public void updateUser(UserCreateRequest request) {
        Integer userInfoId = dslContext
                .select(USERS_INFO.ID)
                .from(USERS_INFO)
                .where(USERS_INFO.EMAIL.eq(request.email()))
                .fetchOneInto(Integer.class);

        if (userInfoId != null) {
            dslContext
                    .update(USERS_INFO)
                    .set(USERS_INFO.PASSWORD, request.password())
                    .where(USERS_INFO.ID.eq(userInfoId))
                    .execute();

            dslContext
                    .update(USERS)
                    .set(USERS.FIO, request.fio())
                    .set(USERS.PHONE_NUMBER, request.phone_number())
                    .where(USERS.USERS_INFO_ID.eq(userInfoId))
                    .execute();
        } else {
            throw new IllegalArgumentException("User with provided email not found.");
        }
    }

    public void deleteUser(String email) {
        Integer userInfoId = dslContext
                .select(USERS_INFO.ID)
                .from(USERS_INFO)
                .where(USERS_INFO.EMAIL.eq(email))
                .fetchOneInto(Integer.class);

        if (userInfoId != null) {
            dslContext
                    .deleteFrom(USERS)
                    .where(USERS.USERS_INFO_ID.eq(userInfoId))
                    .execute();

            dslContext
                    .deleteFrom(USERS_INFO)
                    .where(USERS_INFO.ID.eq(userInfoId))
                    .execute();
        } else {
            throw new IllegalArgumentException("User with provided email not found.");
        }
    }

    public void blockUser(String email) {
        Integer userInfoId = dslContext
                .select(USERS_INFO.ID)
                .from(USERS_INFO)
                .where(USERS_INFO.EMAIL.eq(email))
                .fetchOneInto(Integer.class);

        if (userInfoId != null) {
            dslContext
                    .update(USERS)
                    .set(USERS.STATUS, Status.BLOCKED)
                    .where(USERS.USERS_INFO_ID.eq(userInfoId))
                    .execute();
        } else {
            throw new IllegalArgumentException("User with provided email not found.");
        }
    }

    public void unblockUser(String email) {
        Integer userInfoId = dslContext
                .select(USERS_INFO.ID)
                .from(USERS_INFO)
                .where(USERS_INFO.EMAIL.eq(email))
                .fetchOneInto(Integer.class);

        if (userInfoId != null) {
            dslContext
                    .update(USERS)
                    .set(USERS.STATUS, Status.UNBLOCKED)
                    .where(USERS.USERS_INFO_ID.eq(userInfoId))
                    .execute();
        } else {
            throw new IllegalArgumentException("User with provided email not found.");
        }
    }
}