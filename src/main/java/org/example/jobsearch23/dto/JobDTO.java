package org.example.jobsearch23.dto;

import java.time.LocalDateTime;

public class JobDTO {
    private Long id;
    private Long recruiterId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private Integer salaryMin;
    private Integer salaryMax;
    private String experienceLevel;
    private LocalDateTime postedDate;
    private boolean active;
    private Integer viewsCount;
    private String tags;

    public JobDTO() {}

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRecruiterId() {
        return recruiterId;
    }
    public void setRecruiterId(Long recruiterId) {
        this.recruiterId = recruiterId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRequirements() {
        return requirements;
    }
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getSalaryMin() {
        return salaryMin;
    }
    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }
    public Integer getSalaryMax() {
        return salaryMax;
    }
    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }
    public String getExperienceLevel() {
        return experienceLevel;
    }
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    public LocalDateTime getPostedDate() {
        return postedDate;
    }
    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public Integer getViewsCount() {
        return viewsCount;
    }
    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
}