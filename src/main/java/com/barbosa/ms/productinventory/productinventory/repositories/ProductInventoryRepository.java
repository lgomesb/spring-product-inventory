package com.barbosa.ms.productinventory.productinventory.repositories;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, UUID> {
    
}
