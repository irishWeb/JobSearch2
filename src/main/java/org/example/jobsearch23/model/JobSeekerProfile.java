package org.example.jobsearch23.model;

// src/main/java/com/jobsearch/model/JobseekerProfile.java


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobseeker_profiles")
public class JobseekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String location;

    @Column(name = "desired_position")
    private String desiredPosition;

    @Column(name = "desired_salary")
    private Integer desiredSalary;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resume> resumes = new ArrayList<>();

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

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public Integer getDesiredSalary() {
        return desiredSalary;
    }

    public void setDesiredSalary(Integer desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }

    // Вспомогательные методы для добавления/удаления опыта работы
    public void addWorkExperience(WorkExperience workExperience) {
        workExperiences.add(workExperience);
        workExperience.setProfile(this);
    }

    public void removeWorkExperience(WorkExperience workExperience) {
        workExperiences.remove(workExperience);
        workExperience.setProfile(null);
    }

    // Вспомогательные методы для добавления/удаления резюме
    public void addResume(Resume resume) {
        resumes.add(resume);
        resume.setProfile(this);
    }

    public void removeResume(Resume resume) {
        resumes.remove(resume);
        resume.setProfile(null);
    }
}