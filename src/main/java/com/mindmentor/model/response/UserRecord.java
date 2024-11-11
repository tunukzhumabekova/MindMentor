package com.mindmentor.model.response;

import java.sql.Timestamp;

public record UserRecord (
        Integer id,
        String fio,
        String image,
        Timestamp date_of_registration,
        String phone_number
) {
}