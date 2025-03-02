package hu.zoltan_sipeki.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTConfig;
import hu.cubix.zoltan_sipeki.common_lib.jwt.JWTService;

@SpringBootApplication
@EnableDiscoveryClient
@Import({ JWTService.class })
@EnableConfigurationProperties(JWTConfig.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
