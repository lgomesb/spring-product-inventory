package com.barbosa.ms.productinventory.productinventory.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryRequestDTO {

    @NotNull(message = "{field.product-id.required}")
    private UUID productId;

    @NotNull(message = "{field.product-order-id.required}")
    private UUID productOrderId;

    @NotNull(message = "{field.quantity.required}")
    private Integer quantity;
}
