package com.barbosa.ms.productinventory.product-inventory.services.impl;

import com.barbosa.ms.productinventory.product-inventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.product-inventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.product-inventory.repositories.ProductInventoryRepository;
import com.barbosa.ms.productinventory.product-inventory.services.ProductInventoryService;
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
        ProductInventory ProductInventory = repository.save(new ProductInventory(recordObject.name()) );
        return new ProductInventoryRecord(ProductInventory.getId(), ProductInventory.getName());
    }

    @Override
    public ProductInventoryRecord findById(UUID id) {
        ProductInventory ProductInventory = this.getProductInventoryById(id);
        return new ProductInventoryRecord(ProductInventory.getId(), ProductInventory.getName());
    }

    
    @Override
    public void update(ProductInventoryRecord recordObject) {
        ProductInventory ProductInventory = this.getProductInventoryById(recordObject.id());
        ProductInventory.setName(recordObject.name());
        ProductInventory.setModifieldOn(LocalDateTime.now());
        ProductInventory.setModifiedBy("99999");
        repository.save(ProductInventory);      
    }
    
    @Override
    public void delete(UUID id) {
        ProductInventory ProductInventory = this.getProductInventoryById(id);
        repository.delete(ProductInventory);

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
                    .name(entity.getName())
                    .build())
            .toList();
    }
    
    
}
