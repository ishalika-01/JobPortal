package com.ishalika.SpringJobPortalRevisionJWT.controllers;

import com.ishalika.SpringJobPortalRevisionJWT.model.JobPost;
import com.ishalika.SpringJobPortalRevisionJWT.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:5173")
public class JobRestController {
    @Autowired
    private JobService jobService;
@GetMapping("load")
    public String loadData()
    {
        jobService.load();
        return "success";
    }
    @GetMapping("hello")
    public String hello()
    {

        return "hello world";
    }
    @GetMapping("jobPosts/keyword/{keyword}")
    public List<JobPost> searchByKeyword(@PathVariable("keyword") String keyword)
    {
        return jobService.searchByKeyword(keyword);

    }


    //ResponseBody annotation to specify it is not a view but a response body json
//@GetMapping(path ="jobPosts",produces="application/json")
@GetMapping(path ="jobPosts")
    public List<JobPost> getAllJobs()
    {
return jobService.getAllJobs();
    }
    @GetMapping("jobPost/{postId}")
    public JobPost getJobPost(@PathVariable int postId)
    {
        return jobService.getJob(postId);
    }

    @PostMapping("jobPost")
    public void addJob(@RequestBody JobPost jobPost)
    {

        jobService.addJob(jobPost);

    }
    @PutMapping("jobPost")
    public JobPost updateJob(@RequestBody JobPost jobPost)
    {

        jobService.updateJob(jobPost);
        return jobService.getJob(jobPost.getPostId());


    }
    @DeleteMapping("jobPost/{postId}")
    public void deleteJobPost(@PathVariable int postId)
    {
        jobService.deleteJob(postId);
    }
}
