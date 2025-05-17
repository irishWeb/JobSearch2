package org.example.jobsearch23.service;


import org.example.jobsearch23.dto.RecruiterDTO;
import org.example.jobsearch23.exception.ResourceNotFoundException;
import org.example.jobsearch23.model.RecruiterProfile;
import org.example.jobsearch23.model.User;
import org.example.jobsearch23.repository.RecruiterProfileRepository;
import org.example.jobsearch23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RecruiterProfileService {

    @Autowired
    private RecruiterProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public RecruiterDTO getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        RecruiterProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("RecruiterProfile", "user", user.getId()));
        return mapToDto(profile);
    }

    public RecruiterProfileDTO updateProfile(RecruiterProfileDTO dto) {
        RecruiterProfile profile = profileRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RecruiterProfile", "id", dto.getId()));
        profile.setCompanyName(dto.getCompanyName());
        profile.setCompanyDescription(dto.getCompanyDescription());
        profile.setIndustry(dto.getIndustry());
        profile.setCompanyLocation(dto.getCompanyLocation());
        profile.setLogoUrl(dto.getLogoUrl());
        RecruiterProfile updated = profileRepository.save(profile);
        return mapToDto(updated);
    }

    private RecruiterProfileDTO mapToDto(RecruiterProfile profile) {
        RecruiterProfileDTO dto = new RecruiterProfileDTO();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setCompanyName(profile.getCompanyName());
        dto.setCompanyDescription(profile.getCompanyDescription());
        dto.setIndustry(profile.getIndustry());
        dto.setCompanyLocation(profile.getCompanyLocation());
        dto.setLogoUrl(profile.getLogoUrl());
        // Map jobs if needed
        return dto;
    }
}