package com.project.cartel;

import com.project.cartel.entity.User;
import com.project.cartel.repository.UserRepository;
import com.project.cartel.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EntityScan("com.project.cartel.entity")
@EnableJpaRepositories(basePackages = "com.project.cartel.repository")
@EnableScheduling
@EnableAsync
public class CartelApplication {


	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(CartelApplication.class, args);
		System.out.println("Application started .....");


	}

}
