package com.ishalika.SpringJobPortalRevisionJWT.service;

import com.ishalika.SpringJobPortalRevisionJWT.model.JobPost;
import com.ishalika.SpringJobPortalRevisionJWT.repo.JobRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepo jobRepo;

    @InjectMocks
    private JobService jobService;

    @Test
    void loadSavesDefaultJobs() {
        ArgumentCaptor<List<JobPost>> captor = ArgumentCaptor.forClass(List.class);

        jobService.load();

        verify(jobRepo).saveAll(captor.capture());
        assertThat(captor.getValue()).hasSize(5);
        assertThat(captor.getValue().getFirst().getPostProfile()).isEqualTo("Java Developer");
    }

    @Test
    void searchByKeywordDelegatesToRepository() {
        List<JobPost> expected = List.of(job(1, "Java Developer", "Spring Boot"));
        when(jobRepo.findByPostProfileContainingOrPostDescContaining("java", "java")).thenReturn(expected);

        List<JobPost> result = jobService.searchByKeyword("java");

        assertThat(result).isEqualTo(expected);
        verify(jobRepo).findByPostProfileContainingOrPostDescContaining("java", "java");
    }

    @Test
    void addJobSavesJob() {
        JobPost job = job(1, "Backend Developer", "Java");

        jobService.addJob(job);

        verify(jobRepo).save(job);
    }

    @Test
    void updateJobSavesJob() {
        JobPost job = job(1, "Backend Developer", "Java");

        jobService.updateJob(job);

        verify(jobRepo).save(job);
    }

    @Test
    void getAllJobsReturnsRepositoryJobs() {
        List<JobPost> jobs = List.of(job(1, "Backend Developer", "Java"));
        when(jobRepo.findAll()).thenReturn(jobs);

        List<JobPost> result = jobService.getAllJobs();

        assertThat(result).isEqualTo(jobs);
    }

    @Test
    void getJobReturnsJobWhenPresent() {
        JobPost job = job(1, "Backend Developer", "Java");
        when(jobRepo.findById(1)).thenReturn(Optional.of(job));

        JobPost result = jobService.getJob(1);

        assertThat(result).isEqualTo(job);
    }

    @Test
    void getJobReturnsNullWhenMissing() {
        when(jobRepo.findById(99)).thenReturn(Optional.empty());

        JobPost result = jobService.getJob(99);

        assertThat(result).isNull();
    }

    @Test
    void deleteJobDeletesById() {
        jobService.deleteJob(1);

        verify(jobRepo).deleteById(1);
    }

    private JobPost job(int id, String profile, String description) {
        return new JobPost(id, profile, description, 2, List.of("Java", "Spring Boot"));
    }
}
