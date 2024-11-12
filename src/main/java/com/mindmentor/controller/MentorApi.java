package com.mindmentor.controller;

import com.mindmentor.model.request.MentorCreateRequest;
import com.mindmentor.model.response.MentorGetAllResponse;
import com.mindmentor.model.response.MentorProfileResponse;
import com.mindmentor.service.MentorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentors")
@Tag(name = "Mentor API", description = "Mentor endpoints")
public class MentorApi {
    private final MentorService mentorInfoService;

    @Operation(summary = "Create a new mentor profile", description = "Creates a new mentor profile using the provided request body.")
    @PostMapping("/")
    public void createMentorProfile(@RequestBody MentorCreateRequest request) {
        mentorInfoService.createMentorProfile(request);
    }

    @Operation(summary = "Get mentor info by ID", description = "Retrieves the mentor profile information for a specific mentor ID.")
    @GetMapping("/{mentorInfoId}")
    public MentorProfileResponse getMentorInfoById(@PathVariable int mentorInfoId) {
        return mentorInfoService.getMentorInfoById(mentorInfoId);
    }

    @Operation(summary = "Get all mentor profiles", description = "Returns a list of all mentor profiles available.")
    @GetMapping("/")
    public List<MentorGetAllResponse> getAllMentorInfo() {
        return mentorInfoService.getAllMentorInfo();
    }

    @Operation(summary = "Update mentor info", description = "Updates the mentor profile identified by the specified ID.")
    @PutMapping("/{mentorId}")
    public int updateMentor(@PathVariable int mentorId, @RequestBody MentorCreateRequest request) {
        return mentorInfoService.updateMentorInfo(mentorId, request);
    }

    @Operation(summary = "Delete mentor profile", description = "Deletes a mentor profile using the specified mentor ID.")
    @DeleteMapping("/{mentorInfoId}")
    public int deleteMentorInfo(@PathVariable int mentorInfoId) {
        return mentorInfoService.deleteMentorInfo(mentorInfoId);
    }
}