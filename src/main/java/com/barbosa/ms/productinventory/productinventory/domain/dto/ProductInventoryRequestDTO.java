package com.barbosa.ms.productinventory.productinventory.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotBlank(message = "{field.product-id.not-blank}")
    @NotEmpty(message = "{field.product-id.required}")
    private UUID productId;

    @NotNull(message = "{field.quantity.required}")
    @NotBlank(message = "{field.quantity.not-blank}")
    @NotEmpty(message = "{field.quantity.required}")
    private int quantity;
}
