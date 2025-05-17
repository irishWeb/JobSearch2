package org.example.jobsearch23.repository;



import org.example.jobsearch23.model.RecruiterProfile;
import org.example.jobsearch23.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

    Optional<RecruiterProfile> findByUser(User user);

    Optional<RecruiterProfile> findByUserId(Long userId);
}
