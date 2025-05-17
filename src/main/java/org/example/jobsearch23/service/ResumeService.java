package org.example.jobsearch23.service;


import org.example.jobsearch23.dto.ResumeDTO;
import org.example.jobsearch23.exception.ResourceNotFoundException;
import org.example.jobsearch23.model.JobSeekerProfile;
import org.example.jobsearch23.model.Resume;
import org.example.jobsearch23.model.User;
import org.example.jobsearch23.repository.JobseekerProfileRepository;
import org.example.jobsearch23.repository.ResumeRepository;
import org.example.jobsearch23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final Path root = Paths.get("uploads/resumes");

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobseekerProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Загрузка нового резюме
     */
    public ResumeDTO uploadResume(MultipartFile file) {
        try {
            // Создаем каталог, если его нет
            Files.createDirectories(root);

            // Генерируем уникальное имя файла
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path target = root.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // Находим профиль текущего пользователя
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
            JobSeekerProfile profile = profileRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("JobseekerProfile", "user", user.getId()));

            // Сохраняем запись в БД
            Resume resume = new Resume();
            resume.setProfile(profile);
            resume.setFilePath(target.toString());
            resume.setUploadedAt(LocalDateTime.now());
            resume.setDefault(false);
            Resume saved = resumeRepository.save(resume);

            // Формируем DTO
            ResumeDTO dto = new ResumeDTO();
            dto.setId(saved.getId());
            dto.setTitle(file.getOriginalFilename());
            dto.setFilePath(saved.getFilePath());
            dto.setUploadedAt(saved.getUploadedAt());
            dto.setDefault(saved.isDefault());
            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Could not store file: " + e.getMessage(), e);
        }
    }

    /**
     * Получение списка всех загруженных резюме текущего пользователя
     */
    public List<ResumeDTO> getMyResumes() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        JobSeekerProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("JobseekerProfile", "user", user.getId()));

        return resumeRepository.findByProfile(profile).stream().map(r -> {
            ResumeDTO dto = new ResumeDTO();
            dto.setId(r.getId());
            dto.setTitle(Paths.get(r.getFilePath()).getFileName().toString());
            dto.setFilePath(r.getFilePath());
            dto.setUploadedAt(r.getUploadedAt());
            dto.setDefault(r.isDefault());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Установить резюме по умолчанию
     */
    public void setDefaultResume(Long id) {
        Resume selected = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", id));

        // Сбросить default для всех резюме профиля
        resumeRepository.findByProfile(selected.getProfile()).forEach(r -> {
            if (r.isDefault()) {
                r.setDefault(false);
                resumeRepository.save(r);
            }
        });

        // Установить выбранное по умолчанию
        selected.setDefault(true);
        resumeRepository.save(selected);
    }

    /**
     * Удалить резюме
     */
    public void deleteResume(Long id) {
        Resume toDelete = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", id));
        try {
            Files.deleteIfExists(Paths.get(toDelete.getFilePath()));
        } catch (Exception ignored) { }
        resumeRepository.delete(toDelete);
    }
}