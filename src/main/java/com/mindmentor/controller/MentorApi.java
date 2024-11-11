package com.mindmentor.controller;

import com.mindmentor.model.request.MentorCreateRequest;
import com.mindmentor.model.response.MentorGetAllResponse;
import com.mindmentor.model.response.MentorProfileResponse;
import com.mindmentor.service.MentorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentors")
@Tag(name = "Mentor api", description = "Mentor endpoints")
public class MentorApi {
    private final MentorService mentorInfoService;

    @PostMapping("/")
    public void createMentorProfile(@RequestBody MentorCreateRequest request) {
        mentorInfoService.createMentorProfile(request);
    }

    @GetMapping("/{mentorInfoId}")
    public MentorProfileResponse getMentorInfoById(@PathVariable int mentorInfoId) {
        return mentorInfoService.getMentorInfoById(mentorInfoId);
    }

    @GetMapping("/")
    public List<MentorGetAllResponse> getAllMentorInfo() {
        return mentorInfoService.getAllMentorInfo();
    }

    @PutMapping("/{mentorId}")
    public int updateMentor(@PathVariable int mentorId, @RequestBody MentorCreateRequest request) {
        return mentorInfoService.updateMentorInfo(mentorId, request);
    }

    @DeleteMapping("/{mentorInfoId}")
    public int deleteMentorInfo(@PathVariable int mentorInfoId) {
        return mentorInfoService.deleteMentorInfo(mentorInfoId);
    }
}