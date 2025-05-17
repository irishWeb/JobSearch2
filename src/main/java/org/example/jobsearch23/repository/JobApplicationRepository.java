package org.example.jobsearch23.repository;

import org.example.jobsearch23.model.Job;
import org.example.jobsearch23.model.JobApplication;
import org.example.jobsearch23.model.JobSeekerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJob(Job job);

    Page<JobApplication> findByJob(Job job, Pageable pageable);

    List<JobApplication> findByJobseeker(JobSeekerProfile jobseeker);

    Page<JobApplication> findByJobseeker(JobSeekerProfile jobseeker, Pageable pageable);

    Optional<JobApplication> findByJobAndJobseeker(Job job, JobSeekerProfile jobseeker);

    boolean existsByJobAndJobseeker(Job job, JobSeekerProfile jobseeker);

    long countByJob(Job job);

    long countByJobseeker(JobSeekerProfile jobseeker);
}
