package com.spring.batch.Batch.controller;

import com.spring.batch.Batch.repository.CustomerRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    private CustomerRepository customerRepository;
    @Value("${batch.temp-storage}")
    private  String temp_storage;

    @PostMapping("/importCustomers")
    public void importCSVToDBJob(@RequestParam("file")MultipartFile file) throws IOException {
        String originalFileName=file.getOriginalFilename();
        File fileToImport=new File(temp_storage+"/"+originalFileName);
        file.transferTo(fileToImport);
        JobParameters jobParameter = new JobParametersBuilder()
                .addString("fullPathFileName",temp_storage+originalFileName)
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameter);
            if (jobExecution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED))
            {
                Files.deleteIfExists(Paths.get(temp_storage+originalFileName));
            }
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public void cleardatabase() {
        customerRepository.deleteAll();
    }


}
