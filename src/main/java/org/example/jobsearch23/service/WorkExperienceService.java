package org.example.jobsearch23.service;


import org.example.jobsearch23.dto.WorkExperienceDTO;
import org.example.jobsearch23.exception.ResourceNotFoundException;//FIXME
import org.example.jobsearch23.model.JobSeekerProfile;
import org.example.jobsearch23.model.WorkExperience;
import org.example.jobsearch23.repository.JobseekerProfileRepository;
import org.example.jobsearch23.repository.WorkExperienceRepository;
import org.example.jobsearch23.repository.UserRepository;
import org.example.jobsearch23.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkExperienceService {

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private JobseekerProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Получить все записи опыта текущего соискателя
     */
    public List<WorkExperienceDTO> getMyExperiences() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User", "email", email));
        JobseekerProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobseekerProfile", "user", user.getId()));

        return workExperienceRepository.findByProfile(profile).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Добавить новую запись опыта
     */
    //FIMXE
    public WorkExperienceDTO addExperience(WorkExperienceDTO dto) {
        JobseekerProfile profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobseekerProfile", "id", dto.getProfileId()));

        WorkExperience exp = new WorkExperience();
        exp.setProfile(profile);
        exp.setCompanyName(dto.getCompanyName());
        exp.setPosition(dto.getPosition());
        exp.setStartDate(dto.getStartDate());
        exp.setEndDate(dto.getEndDate());
        exp.setCurrent(dto.isCurrent());
        exp.setDescription(dto.getDescription());
        exp.setLocation(dto.getLocation());

        WorkExperience saved = workExperienceRepository.save(exp);
        return mapToDto(saved);
    }

    /**
     * Обновить существующую запись опыта
     */
    public WorkExperienceDTO updateExperience(WorkExperienceDTO dto) {
        WorkExperience exp = workExperienceRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "WorkExperience", "id", dto.getId()));

        exp.setCompanyName(dto.getCompanyName());
        exp.setPosition(dto.getPosition());
        exp.setStartDate(dto.getStartDate());
        exp.setEndDate(dto.getEndDate());
        exp.setCurrent(dto.isCurrent());
        exp.setDescription(dto.getDescription());
        exp.setLocation(dto.getLocation());

        WorkExperience updated = workExperienceRepository.save(exp);
        return mapToDto(updated);
    }