package com.mindmentor.service.impl;

import com.mindmentor.model.request.MentorCreateRequest;
import com.mindmentor.model.response.MentorGetAllResponse;
import com.mindmentor.model.response.MentorProfileResponse;
import com.mindmentor.repository.MentorInfoRepository;
import com.mindmentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {
    private final MentorInfoRepository mentorInfoRepository;

    @Override
    public void createMentorProfile(MentorCreateRequest request) {
        mentorInfoRepository.createMentorProfile(request);
    }

    @Override
    public MentorProfileResponse getMentorInfoById(int mentorInfoId) {
        return mentorInfoRepository.getMentorById(mentorInfoId);
    }

    @Override
    public List<MentorGetAllResponse> getAllMentorInfo() {
        return mentorInfoRepository.getAllMentorList();
    }

    @Override
    public int updateMentorInfo(int mentorInfoId, MentorCreateRequest request) {
        return mentorInfoRepository.updateMentorInfo(mentorInfoId, request);
    }


    @Override
    public int deleteMentorInfo(int mentorInfoId) {
        return mentorInfoRepository.deleteMentorInfo(mentorInfoId);
    }
}