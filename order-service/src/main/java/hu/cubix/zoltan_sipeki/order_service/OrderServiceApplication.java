package hu.cubix.zoltan_sipeki.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import hu.cubix.zoltan_sipeki.common_lib.exception.GlobalExceptionHandler;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTConfig;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTFilter;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTService;

@SpringBootApplication
@Import({ GlobalExceptionHandler.class, JWTFilter.class, JWTService.class })
@EnableConfigurationProperties(JWTConfig.class)
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
