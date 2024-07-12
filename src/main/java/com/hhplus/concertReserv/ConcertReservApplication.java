package com.hhplus.concertReserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConcertReservApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcertReservApplication.class, args);
	}

}
