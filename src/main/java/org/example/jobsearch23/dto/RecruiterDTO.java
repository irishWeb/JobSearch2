package org.example.jobsearch23.dto;

import java.util.List;

public class RecruiterDTO {
    private Long id;
    private Long userId;
    private String companyName;
    private String companyDescription;
    private String industry;
    private String companyLocation;
    private String logoUrl;
    private List<JobDTO> jobs;

    public void RecruiterProfileDTO() {}

    // Getters and setters omitted for brevity
}