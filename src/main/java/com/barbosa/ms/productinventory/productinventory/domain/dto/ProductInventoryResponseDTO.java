package com.barbosa.ms.productinventory.productinventory.domain.dto;

import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = {"productId", "quantity"}, callSuper = true)
@Data
public class ProductInventoryResponseDTO extends ResponseDTO {

    private UUID productId;
    private UUID productOrderId;
    private Integer quantity;

    public static ProductInventoryResponseDTO create(ProductInventoryRecord productInventoryRecord) {
        return ProductInventoryResponseDTO.builder()
                .id(productInventoryRecord.id())
                .productId(productInventoryRecord.productId())
                .productOrderId(productInventoryRecord.productOrderId())
                .quantity(productInventoryRecord.quantity())
                .build();
    }

    @Builder
    public ProductInventoryResponseDTO(UUID id, UUID productId, UUID productOrderId, Integer quantity) {
        super();
        this.productId = productId;
        this.quantity = quantity;
        this.productOrderId = productOrderId;
        super.setId(id);
    }
}
