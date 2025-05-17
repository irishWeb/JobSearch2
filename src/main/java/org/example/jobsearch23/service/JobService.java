package org.example.jobsearch23.service;

import org.example.jobsearch23.dto.JobDTO;
import org.example.jobsearch23.exception.ResourceNotFoundException;//FIXME
import org.example.jobsearch23.model.Job;
import org.example.jobsearch23.model.RecruiterProfile;
import org.example.jobsearch23.model.User;
import org.example.jobsearch23.repository.JobRepository;
import org.example.jobsearch23.repository.RecruiterProfileRepository;
import org.example.jobsearch23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private UserRepository userRepository;

    private JobDTO mapToDto(Job job) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setRecruiterId(job.getRecruiter().getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setRequirements(job.getRequirements());
        dto.setLocation(job.getLocation());
        dto.setSalaryMin(job.getSalaryMin());
        dto.setSalaryMax(job.getSalaryMax());
        dto.setExperienceLevel(job.getExperienceLevel());
        dto.setPostedDate(job.getPostedDate());
        dto.setActive(job.isActive());
        dto.setViewsCount(job.getViewsCount());
        dto.setTags(job.getTags());
        return dto;
    }

    public Page<JobDTO> getAllActiveJobs(Pageable pageable) {
        return jobRepository.findByActiveTrue(pageable).map(this::mapToDto);
    }

    public Page<JobDTO> searchJobs(String title, String location, String tags,
                                   Integer salaryMin, Integer salaryMax,
                                   String experienceLevel, Pageable pageable) {
        return jobRepository.searchJobs(title, location, tags, salaryMin, salaryMax, experienceLevel, pageable)
                .map(this::mapToDto);
    }

    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        job.setViewsCount(job.getViewsCount() + 1);
        jobRepository.save(job);
        return mapToDto(job);
    }

    public JobDTO createJob(JobDTO jobDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        RecruiterProfile recruiter = recruiterProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("RecruiterProfile", "user", user.getId()));
        Job job = new Job();
        job.setRecruiter(recruiter);
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setSalaryMin(jobDTO.getSalaryMin());
        job.setSalaryMax(jobDTO.getSalaryMax());
        job.setExperienceLevel(jobDTO.getExperienceLevel());
        job.setActive(true);
        job.setTags(jobDTO.getTags());
        job = jobRepository.save(job);
        return mapToDto(job);
    }

    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setSalaryMin(jobDTO.getSalaryMin());
        job.setSalaryMax(jobDTO.getSalaryMax());
        job.setExperienceLevel(jobDTO.getExperienceLevel());
        job.setActive(jobDTO.isActive());
        job.setTags(jobDTO.getTags());
        job = jobRepository.save(job);
        return mapToDto(job);
    }

    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        jobRepository.delete(job);
    }

    public List<JobDTO> getJobsForCurrentRecruiter() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        RecruiterProfile recruiter = recruiterProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("RecruiterProfile", "user", user.getId()));
        return jobRepository.findByRecruiter(recruiter, Pageable.unpaged())
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }
}