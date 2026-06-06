package com.ishalika.SpringJobPortalRevisionJWT.service;

import com.ishalika.SpringJobPortalRevisionJWT.model.JobPost;
import com.ishalika.SpringJobPortalRevisionJWT.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    public JobRepo jobRepo;

    public void load() {
        List<JobPost> jobs2 = new ArrayList<>(List.of(
                new JobPost(1, "Java Developer", "Must have good experience in core Java and advanced Java", 2,
                        List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")),
                new JobPost(2, "Frontend Developer", "Experience in building responsive web applications using React",
                        3, List.of("HTML", "CSS", "JavaScript", "React")),
                new JobPost(3, "Data Scientist", "Strong background in machine learning and data analysis", 4,
                        List.of("Python", "Machine Learning", "Data Analysis")),
                new JobPost(4, "Network Engineer", "Design and implement computer networks for efficient data communication", 5,
                        List.of("Networking", "Cisco", "Routing", "Switching")),
                new JobPost(5, "Mobile App Developer", "Experience in mobile app development for iOS and Android",
                        3, List.of("iOS Development", "Android Development", "Mobile App"))));
        jobRepo.saveAll(jobs2);
    }
public List<JobPost> searchByKeyword(String keyword)
{
    return jobRepo.findByPostProfileContainingOrPostDescContaining(keyword,keyword);
}
    public void addJob(JobPost jobPost) {
//        jobRepo.addJob(jobPost);
        jobRepo.save(jobPost);
        System.out.println("Adding jobs");
    }

    public void updateJob(JobPost jobPost) {
//        jobRepo.updateJob(jobPost);
        jobRepo.save(jobPost);
        System.out.println("Adding jobs");
    }

    public List<JobPost> getAllJobs() {

        return jobRepo.findAll();
    }

    public JobPost getJob(int Id) {
        Optional<JobPost> job = jobRepo.findById(Id);
        return job.orElse(null);
    }

    public void deleteJob(int Id) {

//       jobRepo.deleteJob(Id);
        jobRepo.deleteById(Id);
    }

}
