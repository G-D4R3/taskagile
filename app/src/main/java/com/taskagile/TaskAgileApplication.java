package com.taskagile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TaskAgileApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAgileApplication.class, args);
	}

}
