package com.superhao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages={"com.superhao"})
public class WeiXinDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeiXinDemoApplication.class, args);
	}
}
