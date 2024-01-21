package com.barbosa.ms.productinventory.productinventory.domain.dto;

import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import lombok.Builder;

import java.util.UUID;

public class ProductInventoryResponseDTO extends ResponseDTO {

    public static ProductInventoryResponseDTO create(ProductInventoryRecord productinventoryRecord) {
       return ProductInventoryResponseDTO.builder()
                .id(productinventoryRecord.id())
                .name(productinventoryRecord.name())
                .build();
    }

    @Builder
    public ProductInventoryResponseDTO(UUID id, String name) {
        super();
        super.setId(id);
        super.setName(name);
    }
}
