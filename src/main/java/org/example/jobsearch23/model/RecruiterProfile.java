package org.example.jobsearch23.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruiter_profiles")
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_description", columnDefinition = "TEXT")
    private String companyDescription;

    private String industry;

    @Column(name = "company_location")
    private String companyLocation;

    @Column(name = "logo_url")
    private String logoUrl;

    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs = new ArrayList<>();

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    // Вспомогательные методы для добавления/удаления вакансий
    public void addJob(Job job) {
        jobs.add(job);
        job.setRecruiter(this);
    }

    public void removeJob(Job job) {
        jobs.remove(job);
        job.setRecruiter(null);
    }
}