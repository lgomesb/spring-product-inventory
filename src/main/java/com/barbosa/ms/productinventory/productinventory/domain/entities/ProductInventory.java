package com.barbosa.ms.productinventory.productinventory.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Data
@EqualsAndHashCode(of = {"productId", "productOrderId", "quantity"}, callSuper = false)
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"productId", "productOrderId"}, name = "cnsJustOneByProductAndOrderId")})
@Entity
public class ProductInventory extends AbstractEntity {

    @NotNull(message = "{field.product-id.required}")
    @Column(columnDefinition = "varchar(255) not null")
    private UUID productId;

    @NotNull(message = "{field.product-order-id.required}")
    @Column(columnDefinition = "varchar(255) not null")
    private UUID productOrderId;

    @NotNull(message = "{field.quantity.required}")
    @Column(columnDefinition = "int not null")
    private Integer quantity;

    @Builder()
    public ProductInventory(UUID id, UUID productId, UUID productOrderId, Integer quantity) {
        super();
        super.setId(id);
        this.productId = productId;
        this.quantity = quantity;
        this.productOrderId = productOrderId;

    }
}
