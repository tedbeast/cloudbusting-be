package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {
    public static Logger log = LogManager.getLogger();
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class);
    }
}