package com.barbosa.ms.productinventory.productinventory.domain.mappers;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;

public class ProductInventoryMapper {
    public static ProductInventoryRecord toRecord(ProductInventory productInventory) {
        return ProductInventoryRecord.builder()
                .id(productInventory.getId())
                .productOrderId(productInventory.getProductOrderId())
                .productId(productInventory.getProductId())
                .quantity(productInventory.getQuantity())
                .build();
    }
}
