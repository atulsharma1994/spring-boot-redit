package com.examples.sprigredditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SprigredditcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprigredditcloneApplication.class, args);
	}

}
