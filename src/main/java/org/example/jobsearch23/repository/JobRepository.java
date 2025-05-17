package org.example.jobsearch23.repository;



import org.example.jobsearch23.model.Job;
import org.example.jobsearch23.model.RecruiterProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByActiveTrue(Pageable pageable);

    Page<Job> findByRecruiterAndActiveTrue(RecruiterProfile recruiter, Pageable pageable);

    Page<Job> findByRecruiter(RecruiterProfile recruiter, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.active = true AND " +
            "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:tags IS NULL OR LOWER(j.tags) LIKE LOWER(CONCAT('%', :tags, '%'))) AND " +
            "(:salaryMin IS NULL OR j.salaryMin >= :salaryMin) AND " +
            "(:salaryMax IS NULL OR j.salaryMax <= :salaryMax) AND " +
            "(:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel)")
    Page<Job> searchJobs(@Param("title") String title,
                         @Param("location") String location,
                         @Param("tags") String tags,
                         @Param("salaryMin") Integer salaryMin,
                         @Param("salaryMax") Integer salaryMax,
                         @Param("experienceLevel") String experienceLevel,
                         Pageable pageable);

    List<Job> findTop5ByActiveIsTrueOrderByPostedDateDesc();
}