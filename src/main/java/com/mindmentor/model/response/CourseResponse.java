package com.mindmentor.model.response;

import java.time.LocalDateTime;

public record CourseResponse (
        String course_name,
        String course_description,
        String file_Url,
        Double course_price,
        String user_fio,
        String user_image,
        LocalDateTime las_update,
        String what_you_will_learn
) {
    public CourseResponse(String course_name, String course_description, String file_Url, Double course_price, String user_fio, String user_image, LocalDateTime las_update, String what_you_will_learn) {
        this.course_name = course_name;
        this.course_description = course_description;
        this.file_Url = file_Url;
        this.course_price = course_price;
        this.user_fio = user_fio;
        this.user_image = user_image;
        this.las_update = las_update;
        this.what_you_will_learn = what_you_will_learn;
    }

    public CourseResponse(String course_name, String course_description, String file_Url, Double course_price, String user_fio, String user_image) {
        this(course_name, course_description, file_Url, course_price, user_fio, user_image, null, null);
    }
}
