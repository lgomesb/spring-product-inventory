package com.barbosa.ms.productinventory.productinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles(value = "test")
public class ProductInventoryApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(ProductInventoryApplicationTests.class, args);
	}

}
