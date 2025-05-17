package org.example.jobsearch23.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private RecruiterProfile recruiter;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    private String location;

    @Column(name = "salary_min")
    private Integer salaryMin;

    @Column(name = "salary_max")
    private Integer salaryMax;

    @Column(name = "experience_level")
    private String experienceLevel;

    @Column(name = "posted_date")
    private LocalDateTime postedDate = LocalDateTime.now();

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "views_count")
    private Integer viewsCount = 0;

    @Column(length = 500)
    private String tags;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> applications = new ArrayList<>();

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecruiterProfile getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(RecruiterProfile recruiter) {
        this.recruiter = recruiter;
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

    public List<JobApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<JobApplication> applications) {
        this.applications = applications;
    }

    // Вспомогательные методы для добавления/удаления заявок
    public void addApplication(JobApplication application) {
        applications.add(application);
        application.setJob(this);
    }

    public void removeApplication(JobApplication application) {
        applications.remove(application);
        application.setJob(null);
    }

    // Метод для увеличения счетчика просмотров
    public void incrementViewsCount() {
        this.viewsCount++;
    }
}