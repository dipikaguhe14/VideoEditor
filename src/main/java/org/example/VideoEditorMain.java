package org.example;

import config.ApplicationConfig;
import config.DatabaseConfig;
import config.LoggingConfig;
import config.VideoEditorConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableConfigurationProperties({VideoEditorConfig.class, DatabaseConfig.class, LoggingConfig.class, ApplicationConfig.class})
public class VideoEditorMain  implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(VideoEditorMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initialize();
    }

    public void initialize() {
        System.out.println("Application has been initialized!");
        // Perform initialization tasks here
    }

}