package com.barbosa.ms.productinventory.productinventory.domain.records;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductInventoryRecord(UUID id, UUID productId, UUID productOrderId, Integer quantity) {
}
