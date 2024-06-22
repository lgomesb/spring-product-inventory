package com.barbosa.ms.productinventory.productinventory.repositories.success;

import com.barbosa.ms.productinventory.productinventory.ProductInventoryApplicationTests;
import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@DataJpaTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = ProductInventoryApplicationTests.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductInventoryRepositorySuccessTest {

    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ORDER_ID = UUID.randomUUID();
    private static final Integer QUANTITY = 1;
    
    @Autowired
    private ProductInventoryRepository repository;

    
    @Test 
    @Order(0)
    void shouldSuccessfulInjectComponent() {
        assertNotNull(repository);
    }

    @Order(1)
    @Test
    void shouldWhenCallCreate() {
        ProductInventory productinventory = repository.saveAndFlush(getProductInventory());
        assertNotNull(productinventory, "Should return ProductInventory is not null");
        assertNotNull(productinventory.getId());
        assertEquals(PRODUCT_ID, productinventory.getProductId());
    }


    @Order(2)
    @Test
    void shouldWhenCallFindById() {
        ProductInventory productinventory = repository.save(getProductInventory());
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        assertNotNull(oProductInventory.get(), "Should return ProductInventory is not null");
        assertNotNull(oProductInventory.get().getId(), "Should return ProductInventory ID is not null");
        assertNotNull(oProductInventory.get().getProductId(), "Should return ProductInventory ProductId is not null");
    }

    private ProductInventory getProductInventory() {
        return new ProductInventory(UUID.randomUUID(), PRODUCT_ID, PRODUCT_ORDER_ID, QUANTITY);
    }


    @Order(3)
    @Test
    void shouldWhenCallUpdate() {
        ProductInventory productinventory = repository.save(getProductInventory());
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        ProductInventory newProductInventory = oProductInventory.get();
        newProductInventory.setProductId(UUID.randomUUID());
        newProductInventory.setQuantity(2);
        newProductInventory = repository.save(newProductInventory);
        assertEquals(2, newProductInventory.getQuantity());
    }
  
    @Order(4)
    @Test
    void shouldWhenCallDelete() {
        ProductInventory productinventory = repository.save(getProductInventory());
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        repository.delete(oProductInventory.get());
        Optional<ProductInventory> findProductInventory = repository.findById(oProductInventory.get().getId());
        assertFalse(findProductInventory.isPresent());
    }
}
