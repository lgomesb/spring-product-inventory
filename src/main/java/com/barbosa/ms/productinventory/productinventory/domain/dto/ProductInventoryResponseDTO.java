package com.barbosa.ms.productinventory.productinventory.domain.dto;

import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = {"productId", "quantity"}, callSuper = true)
@Data
public class ProductInventoryResponseDTO extends ResponseDTO {

    private UUID productId;
    private Integer quantity;

    public static ProductInventoryResponseDTO create(ProductInventoryRecord productInventoryRecord) {
        return ProductInventoryResponseDTO.builder()
                .id(productInventoryRecord.id())
                .productId(productInventoryRecord.productId())
                .quantity(productInventoryRecord.quantity())
                .build();
    }

    @Builder
    public ProductInventoryResponseDTO(UUID id, UUID productId, Integer quantity) {
        super();
        this.productId = productId;
        this.quantity = quantity;
        super.setId(id);
    }
}
