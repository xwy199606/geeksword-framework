package org.geeksword.spring.boot;

import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import org.geeksword.spring.boot.elastic.annotations.EnableElasticJob;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;
import java.util.concurrent.locks.LockSupport;

@EnableConfigurationProperties(ElasticJobConfig.class)
@EnableElasticJob
@SpringBootApplication(scanBasePackages = "org.geeksword.*")
public class StartApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
        LockSupport.park();
    }


    @Autowired
    private SpringJobScheduler stringSpringJobSchedulerMap;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("StartApp.run");
    }
}
