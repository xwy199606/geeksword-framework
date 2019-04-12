package org.geeksword.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@SpringBootApplication
public class GeekswordWebTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeekswordWebTestApplication.class, args);
    }


    @GetMapping
    public Object test() {
        ResponseDemo responseDemo = new ResponseDemo();
        responseDemo.setAge(1);
        responseDemo.setName("zls");
        return responseDemo;
    }
}
