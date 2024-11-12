package com.sec.gen.next.outbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;


@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
public class OutboundApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutboundApplication.class, args);
	}

}
