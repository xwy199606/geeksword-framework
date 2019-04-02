package org.geeksword.spring.boot;

import org.geeksword.spring.boot.elastic.annotations.EnableElasticJob;
import org.geeksword.spring.boot.elastic.config.ElasticJobConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.locks.LockSupport;

@EnableConfigurationProperties(ElasticJobConfig.class)
@EnableElasticJob
@SpringBootApplication
        (scanBasePackages = "org.geeksword.*")
public class StartApp {

    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
        LockSupport.park();
    }

}
