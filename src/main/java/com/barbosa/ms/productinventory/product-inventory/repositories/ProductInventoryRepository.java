package com.barbosa.ms.productinventory.product-inventory.repositories;

import com.barbosa.ms.productinventory.product-inventory.domain.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, UUID> {
    
}
