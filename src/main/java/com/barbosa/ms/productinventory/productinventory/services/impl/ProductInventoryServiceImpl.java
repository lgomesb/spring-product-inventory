package com.barbosa.ms.productinventory.productinventory.services.impl;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import com.barbosa.ms.productinventory.productinventory.services.ProductInventoryService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Autowired
    private ProductInventoryRepository repository;

    @Override
    public ProductInventoryRecord create(ProductInventoryRecord recordObject) {
        ProductInventory productinventory = repository.save(
                ProductInventory.builder()
                .productId(recordObject.productId())
                .quantity(recordObject.quantity())
                .build());
        return new ProductInventoryRecord(productinventory.getId(),
                productinventory.getProductId(), productinventory.getQuantity() );
    }

    @Override
    public ProductInventoryRecord findById(UUID id) {
        ProductInventory productinventory = this.getProductInventoryById(id);
        return new ProductInventoryRecord(productinventory.getId(),
                productinventory.getProductId(), productinventory.getQuantity());
    }

    
    @Override
    public void update(ProductInventoryRecord recordObject) {
        ProductInventory productinventory = this.getProductInventoryById(recordObject.id());
        productinventory.setProductId(recordObject.productId());
        productinventory.setQuantity(recordObject.quantity());
        productinventory.setModifieldOn(LocalDateTime.now());
        productinventory.setModifiedBy("99999");
        repository.save(productinventory);
    }
    
    @Override
    public void delete(UUID id) {
        ProductInventory productinventory = this.getProductInventoryById(id);
        repository.delete(productinventory);

    }
    
    private ProductInventory getProductInventoryById(UUID id) {
        return repository.findById(id)
                  .orElseThrow(()-> new ObjectNotFoundException("ProductInventory", id));
    }

    @Override
    public List<ProductInventoryRecord> listAll() {
        return repository.findAll()
            .stream()
            .map(entity -> ProductInventoryRecord.builder()
                    .id(entity.getId())
                    .productId(entity.getProductId())
                    .quantity(entity.getQuantity())
                    .build())
            .toList();
    }
    
    
}
