package org.geeksword.spring.boot;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.geeksword.spring.boot.elastic.annotations.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.locks.LockSupport;

@EnableApolloConfig
@EnableElasticJob
@SpringBootApplication
public class StartApp {

    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
        LockSupport.park();
    }

}
