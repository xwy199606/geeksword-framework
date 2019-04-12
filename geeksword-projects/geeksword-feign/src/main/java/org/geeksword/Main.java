package org.geeksword;

import org.geeksword.feign.RemoteService;
import org.geeksword.feign.ResponseDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import java.rmi.Remote;

@EnableFeignClients
@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private RemoteService remoteService;

    @Override
    public void run(String... strings) throws Exception {
        ResponseDemo test = remoteService.test();
        System.out.println(test);
    }
}
