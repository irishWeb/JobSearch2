package org.example.jobsearch23.service;


import org.example.jobsearch23.dto.JobApplicationDTO;
import org.example.jobsearch23.ResourceNotFoundException;//FIXME
import org.example.jobsearch23.model.Job;
import org.example.jobsearch23.model.JobApplication;
import org.example.jobsearch23.model.JobSeekerProfile;
import org.example.jobsearch23.model.User;
import org.example.jobsearch23.repository.JobApplicationRepository;
import org.example.jobsearch23.repository.JobRepository;
import org.example.jobsearch23.repository.JobseekerProfileRepository;
import org.example.jobsearch23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobseekerProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public JobApplicationDTO apply(JobApplicationDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        JobseekerProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobseekerProfile", "user", user.getId()));
        Job job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", dto.getJobId()));
        if (applicationRepository.existsByJobAndJobseeker(job, profile)) {
            throw new RuntimeException("Already applied to this job");
        }
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setJobseeker(profile);
        application.setCoverLetter(dto.getCoverLetter());
        application = applicationRepository.save(application);
        return mapToDto(application);
    }

    public List<JobApplicationDTO> getMyApplications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        JobSeekerProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobseekerProfile", "user", user.getId()));
        return applicationRepository.findByJobseeker(profile)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getApplicationsForJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
        return applicationRepository.findByJob(job)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private JobApplicationDTO mapToDto(JobApplication app) {
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setId(app.getId());
        dto.setJobId(app.getJob().getId());
        dto.setJobseekerProfileId(app.getJobseeker().getId());