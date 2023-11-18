package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com"})
@MapperScan("com.mapper")
public class HumanResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanResourcesApplication.class, args);
	}

}
