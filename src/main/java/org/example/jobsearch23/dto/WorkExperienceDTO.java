package org.example.jobsearch23.dto;

import java.time.LocalDate;

public class WorkExperienceDTO {
    private Long id;
    private Long profileId;
    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean current;
    private String description;
    private String location;

    public WorkExperienceDTO() {}

    // Getters and setters omitted for brevity
    //FIMXE
}