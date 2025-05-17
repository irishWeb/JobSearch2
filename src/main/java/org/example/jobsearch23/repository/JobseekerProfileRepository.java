package org.example.jobsearch23.repository;


import org.example.jobsearch23.model.JobSeekerProfile;
import org.example.jobsearch23.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobseekerProfileRepository extends JpaRepository<JobSeekerProfile, Long> {

    Optional<JobSeekerProfile> findByUser(User user);

    Optional<JobSeekerProfile> findByUserId(Long userId);
}
