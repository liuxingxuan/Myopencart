package io.lxx.opencartadminwebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "io.lxx")
@EnableCaching
public class OpencartAdminWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpencartAdminWebapiApplication.class, args);
    }

}

