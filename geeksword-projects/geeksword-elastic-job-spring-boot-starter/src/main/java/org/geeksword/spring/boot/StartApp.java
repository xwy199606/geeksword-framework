package org.geeksword.spring.boot;

import org.geeksword.spring.boot.elastic.test.TestJob;
import org.geeksword.spring.boot.elastic.annotations.EnableElasticJob;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.locks.LockSupport;

@EnableConfigurationProperties(ElasticJobConfig.class)
@EnableElasticJob(enableJobEventConfiguration = true)
@SpringBootApplication(scanBasePackages = "org.geeksword.*")
public class StartApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
        LockSupport.park();
    }

    @Autowired
    private TestJob testJob;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("StartApp.run");
    }
}
