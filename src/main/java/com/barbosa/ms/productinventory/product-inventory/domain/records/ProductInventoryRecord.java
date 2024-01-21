package com.barbosa.ms.productinventory.product-inventory.domain.records;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductInventoryRecord(UUID id, String name) {
    
}
