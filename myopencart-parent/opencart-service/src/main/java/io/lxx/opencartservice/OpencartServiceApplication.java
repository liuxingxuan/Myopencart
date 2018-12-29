package io.lxx.opencartservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.lxx.opencartservice.dao")
public class OpencartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpencartServiceApplication.class, args);
    }

}

