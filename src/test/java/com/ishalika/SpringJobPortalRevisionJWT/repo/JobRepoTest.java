package com.ishalika.SpringJobPortalRevisionJWT.repo;

import com.ishalika.SpringJobPortalRevisionJWT.model.JobPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JobRepoTest {

    @Autowired
    private JobRepo jobRepo;

    @Test
    void searchByKeywordReturnsJobWhenProfileMatches() {
        jobRepo.save(job(1, "Java Developer", "Build backend services"));
        jobRepo.save(job(2, "Frontend Developer", "Build React UI"));

        List<JobPost> result = jobRepo.findByPostProfileContainingOrPostDescContaining("Java", "Java");

        assertThat(result).extracting(JobPost::getPostProfile).containsExactly("Java Developer");
    }

    @Test
    void searchByKeywordReturnsJobWhenDescriptionMatches() {
        jobRepo.save(job(1, "Backend Developer", "Build Spring Boot services"));
        jobRepo.save(job(2, "Frontend Developer", "Build React UI"));

        List<JobPost> result = jobRepo.findByPostProfileContainingOrPostDescContaining("Spring", "Spring");

        assertThat(result).extracting(JobPost::getPostProfile).containsExactly("Backend Developer");
    }

    @Test
    void searchByKeywordReturnsEmptyListWhenNoMatchExists() {
        jobRepo.save(job(1, "Backend Developer", "Build Spring Boot services"));

        List<JobPost> result = jobRepo.findByPostProfileContainingOrPostDescContaining("Python", "Python");

        assertThat(result).isEmpty();
    }

    private JobPost job(int id, String profile, String description) {
        return new JobPost(id, profile, description, 2, List.of("Java", "Spring Boot"));
    }
}
