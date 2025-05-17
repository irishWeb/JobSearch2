package org.example.jobsearch23.dto;

import java.time.LocalDateTime;

public class JobApplicationDTO {
    private Long id;
    private Long jobId;
    private Long jobseekerProfileId;
    private String coverLetter;
    private LocalDateTime applicationDate;
    private String status;

    public JobApplicationDTO() {}

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getJobId() {
        return jobId;
    }
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    public Long getJobseekerProfileId() {
        return jobseekerProfileId;
    }
    public void setJobseekerProfileId(Long jobseekerProfileId) {
        this.jobseekerProfileId = jobseekerProfileId;
    }
    public String getCoverLetter() {
        return coverLetter;
    }
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}