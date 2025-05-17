package org.example.jobsearch23.service;


import org.example.jobsearch23.dto.JobSeekerDTO;
import  org.example.jobsearch23.exception.ResourceNotFoundException;//FIXME
import  org.example.jobsearch23.model.JobSeekerProfile;
import  org.example.jobsearch23.model.User;
import  org.example.jobsearch23.repository.JobseekerProfileRepository;
import  org.example.jobsearch23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JobseekerProfileService {

    @Autowired
    private JobseekerProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public JobSeekerDTO getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        JobSeekerProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobseekerProfile", "user", user.getId()));
        return mapToDto(profile);
    }

    public JobseekerDTO updateProfile(JobseekerDTO dto) {
        JobSeekerProfile profile = profileRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("JobseekerProfile", "id", dto.getId()));
        profile.setHeadline(dto.getHeadline());
        profile.setSummary(dto.getSummary());
        profile.setLocation(dto.getLocation());
        profile.setDesiredPosition(dto.getDesiredPosition());
        profile.setDesiredSalary(dto.getDesiredSalary());
        profile.setExperienceYears(dto.getExperienceYears());
        JobSeekerProfile updated = profileRepository.save(profile);
        return mapToDto(updated);
    }

    private JobseekerDTO mapToDto(JobSeekerProfile profile) {
        JobseekerDTO dto = new JobseekerDTO();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setHeadline(profile.getHeadline());
        dto.setSummary(profile.getSummary());
        dto.setLocation(profile.getLocation());
        dto.setDesiredPosition(profile.getDesiredPosition());
        dto.setDesiredSalary(profile.getDesiredSalary());
        dto.setExperienceYears(profile.getExperienceYears());
        // Map work experiences and resumes if needed
        return dto;
    }
}