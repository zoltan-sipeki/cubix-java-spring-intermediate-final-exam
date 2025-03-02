package hu.cubix.zoltan_sipeki.catalog_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import hu.cubix.zoltan_sipeki.catalog_service.service.InitDbService;
import hu.cubix.zoltan_sipeki.common_lib.exception.GlobalExceptionHandler;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTConfig;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTFilter;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
@Import({ GlobalExceptionHandler.class, JWTFilter.class, JWTService.class })
@EnableConfigurationProperties(JWTConfig.class)
public class CatalogServiceApplication implements CommandLineRunner {

	private final InitDbService initDbService;
	
	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDbService.init();		
	}

}
