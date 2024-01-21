package com.barbosa.ms.productinventory.product-inventory.domain.dto;

import com.barbosa.ms.productinventory.product-inventory.domain.records.ProductInventoryRecord;
import lombok.Builder;

import java.util.UUID;

public class ProductInventoryResponseDTO extends ResponseDTO {

    public static ProductInventoryResponseDTO create(ProductInventoryRecord ProductInventoryRecord) {
       return ProductInventoryResponseDTO.builder()
                .id(ProductInventoryRecord.id())
                .name(ProductInventoryRecord.name())
                .build();
    }

    @Builder
    public ProductInventoryResponseDTO(UUID id, String name) {
        super();
        super.setId(id);
        super.setName(name);
    }
}
