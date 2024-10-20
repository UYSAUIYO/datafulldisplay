package org.example.datafulldisplay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("org.example.datafulldisplay.mapper")
public class DatafulldisplayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatafulldisplayApplication.class, args);
    }

}
