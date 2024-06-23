package com.barbosa.ms.productinventory.productinventory.services.impl;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.domain.mappers.ProductInventoryMapper;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import com.barbosa.ms.productinventory.productinventory.services.ProductInventoryService;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final ProductInventoryRepository repository;
    private final ModelMapper mapper;

    public ProductInventoryServiceImpl(ProductInventoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductInventoryRecord create(ProductInventoryRecord recordInput) {
        try {
            ProductInventory productinventory = mapper.map(recordInput, ProductInventory.class);
            repository.save(productinventory);
            return ProductInventoryMapper.toRecord(productinventory);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("There must be only one inventory per product and order");
        }
    }

    @Override
    public ProductInventoryRecord findById(UUID id) {
        ProductInventory productinventory = this.getProductInventoryById(id);
        return ProductInventoryMapper.toRecord(productinventory);
    }

    
    @Override
    public void update(ProductInventoryRecord recordObject) {
        ProductInventory productinventory = this.getProductInventoryById(recordObject.id());
        productinventory.setProductId(recordObject.productId());
        productinventory.setProductOrderId(recordObject.productOrderId());
        productinventory.setQuantity(recordObject.quantity());
        repository.save(productinventory);
    }
    
    @Override
    public void delete(UUID id) {
        ProductInventory productinventory = this.getProductInventoryById(id);
        repository.delete(productinventory);
    }
    
    @Override
    public List<ProductInventoryRecord> listAll() {
        return repository.findAll()
            .stream()
            .map(ProductInventoryMapper::toRecord)
            .toList();
    }

    private ProductInventory getProductInventoryById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("ProductInventory", id));
    }

    
}
