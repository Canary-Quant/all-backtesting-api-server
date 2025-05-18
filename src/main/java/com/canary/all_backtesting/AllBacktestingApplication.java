package com.canary.all_backtesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AllBacktestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllBacktestingApplication.class, args);
	}

}
