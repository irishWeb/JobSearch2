package org.example.jobsearch23.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "jobseeker_id"}))
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "jobseeker_id", nullable = false)
    private JobseekerProfile jobseeker;

    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "application_date")
    private LocalDateTime applicationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.pending;

    // Enum для статуса заявки
    public enum ApplicationStatus {
        pending, reviewed, interviewed, rejected, offered, hired
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobseekerProfile getJobseeker() {
        return jobseeker;
    }

    public void setJobseeker(JobseekerProfile jobseeker) {
        this.jobseeker = jobseeker;
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}