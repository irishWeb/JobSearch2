package org.example.jobsearch23.dto;

import java.util.List;

public class JobSeekerDTO {
    private Long id;
    private Long userId;
    private String headline;
    private String summary;
    private String location;
    private String desiredPosition;
    private Integer desiredSalary;
    private Integer experienceYears;
    private List<WorkExperienceDTO> workExperiences;
    private List<ResumeDTO> resumes;

    public void JobseekerProfileDTO() {}

    // Getters and setters omitted for brevity
}