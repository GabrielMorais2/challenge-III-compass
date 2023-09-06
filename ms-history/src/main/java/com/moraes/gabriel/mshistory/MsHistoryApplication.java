package com.moraes.gabriel.mshistory;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHistoryApplication.class, args);
	}

}
