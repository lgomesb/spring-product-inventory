package com.barbosa.ms.productinventory.product-inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductInventoryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductInventoryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("ProductInventory");
    }
}