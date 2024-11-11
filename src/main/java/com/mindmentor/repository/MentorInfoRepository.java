package com.mindmentor.repository;

import com.example.public_.enums.Role;
import com.example.public_.enums.Status;
import com.example.public_.tables.records.*;
import com.mindmentor.model.request.MentorCreateRequest;
import com.mindmentor.model.response.CourseResponse;
import com.mindmentor.model.response.MentorGetAllResponse;
import com.mindmentor.model.response.MentorProfileResponse;
import com.mindmentor.model.response.ServiceResponse;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.public_.Tables.*;
import static com.example.public_.tables.MentorInfo.MENTOR_INFO;
import static com.example.public_.tables.Users.USERS;
import static com.example.public_.tables.UsersInfo.USERS_INFO;

@Repository
public class MentorInfoRepository {
    private final DSLContext dslContext;

    public MentorInfoRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void createMentorProfile(MentorCreateRequest request) {
        Integer usersInfoId = Objects.requireNonNull(dslContext.insertInto(USERS_INFO,
                                USERS_INFO.EMAIL,
                                USERS_INFO.PASSWORD,
                                USERS_INFO.ROLE)
                        .values(request.profileRequest().email(),
                                request.profileRequest().password(),
                                Role.MENTOR)
                        .returning(USERS_INFO.ID)
                        .fetchOne())
                .getValue(USERS_INFO.ID);

        Integer userId = Objects.requireNonNull(dslContext.insertInto(USERS,
                                USERS.FIO,
                                USERS.IMAGE,
                                USERS.DATE_OF_REGISTRATION,
                                USERS.STATUS,
                                USERS.PHONE_NUMBER,
                                USERS.USERS_INFO_ID)
                        .values(request.profileRequest().fio(),
                                null,
                                LocalDateTime.now(),
                                Status.UNBLOCKED,
                                request.profileRequest().phoneNumber(),
                                usersInfoId)
                        .returning(USERS.ID)
                        .fetchOne())
                .getValue(USERS.ID);

        dslContext.insertInto(MENTOR_INFO,
                        MENTOR_INFO.DIRECTION_ID,
                        MENTOR_INFO.LANGUAGE,
                        MENTOR_INFO.EXPERIENCE,
                        MENTOR_INFO.ABOUT_MENTOR,
                        MENTOR_INFO.VIDEO_URL,
                        MENTOR_INFO.USERS_ID)
                .values(request.directionId(),
                        request.language(),
                        request.experience(),
                        request.aboutMentor(),
                        request.videoUrl(),
                        userId)
                .execute();
    }

    public MentorProfileResponse getMentorById(int mentorInfoId) {
        MentorInfoRecord mentorRecord = dslContext.selectFrom(MENTOR_INFO)
                .where(MENTOR_INFO.ID.eq(mentorInfoId))
                .fetchOne();

        assert mentorRecord != null;

        UsersRecord userRecord = dslContext.selectFrom(USERS)
                .where(USERS.ID.eq(mentorRecord.getUsersId()))
                .fetchOne();

        assert userRecord != null;

        DirectionRecord directionRecord = dslContext.selectFrom(DIRECTION)
                .where(DIRECTION.ID.eq(mentorRecord.getDirectionId()))
                .fetchOne();

        assert directionRecord != null;

        List<CoursesRecord> coursesRecords = dslContext.selectFrom(COURSES)
                .where(COURSES.MENTOR_INFO_ID.eq(mentorInfoId))
                .fetchInto(CoursesRecord.class);

        List<CourseResponse> courses = new ArrayList<>();
        for (CoursesRecord course : coursesRecords) {
            CourseResponse courseResponse = new CourseResponse(
                    course.getCourseName(),
                    course.getDescription(),
                    course.getFileUrl(),
                    course.getPrice(),
                    userRecord.getFio(),
                    userRecord.getImage()
            );
            courses.add(courseResponse);
        }

        List<ServiceRecord> services = dslContext.selectFrom(SERVICE)
                .where(SERVICE.USERS_ID.eq(mentorRecord.getUsersId()))
                .fetchInto(ServiceRecord.class);

        List<ServiceResponse> servicesWithPrices = new ArrayList<>();
        for (ServiceRecord service : services) {
            PriceOfServiceRecord priceRecord = dslContext.selectFrom(PRICE_OF_SERVICE)
                    .where(PRICE_OF_SERVICE.SERVICE_ID.eq(service.getId())
                            .and(PRICE_OF_SERVICE.MENTOR_ID.eq(mentorInfoId)))
                    .fetchOne();

            if (priceRecord != null) {
                servicesWithPrices.add(new ServiceResponse(
                        service.getName(),
                        service.getDescription(),
                        priceRecord.getPrice()
                ));
            }
        }

        return new MentorProfileResponse(
                userRecord.getFio(),
                userRecord.getImage(),
                directionRecord.getName(),
                mentorRecord.getLanguage(),
                mentorRecord.getExperience(),
                mentorRecord.getAboutMentor(),
                mentorRecord.getVideoUrl(),
                courses,
                servicesWithPrices
        );
    }

    public List<MentorGetAllResponse> getAllMentorList() {
        List<MentorInfoRecord> mentorRecords = dslContext.selectFrom(MENTOR_INFO)
                .fetchInto(MentorInfoRecord.class);

        List<MentorGetAllResponse> mentorResponses = new ArrayList<>();

        for (MentorInfoRecord mentorRecord : mentorRecords) {
            UsersRecord userRecord = dslContext.selectFrom(USERS)
                    .where(USERS.ID.eq(mentorRecord.getUsersId()))
                    .fetchOne();

            DirectionRecord directionRecord = dslContext.selectFrom(DIRECTION)
                    .where(DIRECTION.ID.eq(mentorRecord.getDirectionId()))
                    .fetchOne();

            List<ServiceRecord> services = dslContext.selectFrom(SERVICE)
                    .where(SERVICE.USERS_ID.eq(mentorRecord.getUsersId()))
                    .fetchInto(ServiceRecord.class);

            List<PriceOfServiceRecord> prices = dslContext.selectFrom(PRICE_OF_SERVICE)
                    .where(PRICE_OF_SERVICE.MENTOR_ID.eq(mentorRecord.getId()))
                    .fetchInto(PriceOfServiceRecord.class);

            List<FeedbackRecord> feedbacks = dslContext.selectFrom(FEEDBACK)
                    .where(FEEDBACK.MENTOR_INFO_ID.eq(mentorRecord.getId()))
                    .fetchInto(FeedbackRecord.class);

            double averageRating = feedbacks.stream()
                    .mapToDouble(FeedbackRecord::getRating)
                    .average()
                    .orElse(0.0);

            double price = prices.stream()
                    .mapToDouble(PriceOfServiceRecord::getPrice)
                    .findFirst()
                    .orElse(0.0);

            assert directionRecord != null;
            assert userRecord != null;

            List<ServiceResponse> serviceResponses = new ArrayList<>();
            for (ServiceRecord serviceRecord : services) {
                ServiceResponse serviceResponse = new ServiceResponse(
                        serviceRecord.getName()
                );

                serviceResponses.add(serviceResponse);
            }

            MentorGetAllResponse response = new MentorGetAllResponse(
                    userRecord.getFio(),
                    userRecord.getImage(),
                    directionRecord.getName(),
                    mentorRecord.getExperience(),
                    mentorRecord.getAboutMentor(),
                    mentorRecord.getVideoUrl(),
                    serviceResponses,
                    averageRating,
                    price
            );

            mentorResponses.add(response);
        }

        return mentorResponses;
    }

    public int updateMentorInfo(int mentorInfoId, MentorCreateRequest request) {
        return dslContext.update(MENTOR_INFO)
                .set(MENTOR_INFO.DIRECTION_ID, request.directionId())
                .set(MENTOR_INFO.LANGUAGE, request.language())
                .set(MENTOR_INFO.EXPERIENCE, request.experience())
                .set(MENTOR_INFO.ABOUT_MENTOR, request.aboutMentor())
                .set(MENTOR_INFO.VIDEO_URL, request.videoUrl())
                .where(MENTOR_INFO.ID.eq(mentorInfoId))
                .execute();
    }

    public int deleteMentorInfo(int mentorInfoId) {
        return dslContext.deleteFrom(MENTOR_INFO)
                .where(MENTOR_INFO.ID.eq(mentorInfoId))
                .execute();
    }
}