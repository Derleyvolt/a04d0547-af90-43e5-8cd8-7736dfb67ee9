package com.test.checkout;

import com.test.checkout.datasource.TenantDatasourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TenantDatasourceProperties.class)
public class CheckoutApplication {
	public static void main(String[] args) {
		SpringApplication.run(CheckoutApplication.class, args);
	}

}
