package br.com.gym.management.planapi;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableRabbit
@EnableCaching
@EnableDiscoveryClient
public class PlanApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanApiApplication.class, args);
    }

}