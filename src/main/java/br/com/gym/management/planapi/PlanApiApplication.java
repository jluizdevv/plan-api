package br.com.gym.management.planapi;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PlanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanApiApplication.class, args);
	}

}
