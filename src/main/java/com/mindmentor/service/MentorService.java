package com.mindmentor.service;

import com.mindmentor.model.request.MentorCreateRequest;
import com.mindmentor.model.response.MentorGetAllResponse;
import com.mindmentor.model.response.MentorProfileResponse;

import java.util.List;

public interface MentorService {
    void createMentorProfile(MentorCreateRequest request);
    MentorProfileResponse getMentorInfoById(int mentorInfoId);
    List<MentorGetAllResponse> getAllMentorInfo();
    int updateMentorInfo(int mentorInfoId, MentorCreateRequest request);
    int deleteMentorInfo(int mentorInfoId);
}