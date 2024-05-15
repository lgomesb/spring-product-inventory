package com.barbosa.ms.productinventory.productinventory.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@Table(name = "productInventory")
@Entity
public class ProductInventory extends AbstractEntity {

    @NotNull(message = "{field.product-id.required}")
    @NotBlank(message = "{field.product-id.not-blank}")
    @NotEmpty(message = "{field.product-id.required}")
    @Column(columnDefinition = "varchar(255) not null")
    private UUID productId;

    @NotNull(message = "{field.quantity.required}")
    @NotBlank(message = "{field.quantity.not-blank}")
    @NotEmpty(message = "{field.quantity.required}")
    @Column(columnDefinition = "int not null")
    private Integer quantity;

    @Builder()
    public ProductInventory(UUID id, UUID productId, Integer quantity) {
        super();
        super.setId(id);
        this.productId = productId;
        this.quantity = quantity;

    }
}
