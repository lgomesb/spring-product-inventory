package com.barbosa.ms.productinventory.productinventory.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Table(name = "productinventory")
@Entity
public class ProductInventory extends AbstractEntity {
    
    public ProductInventory(String name) {
        super();
        super.setName(name);
    }
    
    @Builder()
    public ProductInventory(UUID id, String name) {
        super();
        super.setId(id);
        super.setName(name);
    }
}
