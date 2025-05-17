package org.example.jobsearch23.repository;



import org.example.jobsearch23.model.JobSeekerProfile;
import org.example.jobsearch23.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByProfile(JobSeekerProfile profile);

    Optional<Resume> findByProfileAndIsDefaultTrue(JobSeekerProfile profile);

    int countByProfile(JobSeekerProfile profile);
}