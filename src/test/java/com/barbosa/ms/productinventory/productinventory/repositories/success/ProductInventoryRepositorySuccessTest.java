package com.barbosa.ms.productinventory.productinventory.repositories.success;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.barbosa.ms.productinventory.productinventory.ProductInventoryApplicationTests;
import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;

@ActiveProfiles(value = "test")
@DataJpaTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = ProductInventoryApplicationTests.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductInventoryRepositorySuccessTest {

    @Autowired
    private ProductInventoryRepository repository;

    private static Stream<Arguments> provideProductInventoryData() {        
        return Stream.of(
          Arguments.of("ProductInventory-Test-01"),
          Arguments.of("ProductInventory-Test-02")
        );
    }

    
    @Test 
    @Order(0)
    void shouldSuccessfulInjectComponent() {
        assertNotNull(repository);
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldWhenCallCreate(String productinventoryName) {
        ProductInventory productinventory = repository.saveAndFlush(new ProductInventory(productinventoryName));
        assertNotNull(productinventory, "Should return ProductInventory is not null");
        assertNotNull(productinventory.getId());
        assertEquals(productinventoryName, productinventory.getName());        
    }


    @Order(2)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldWhenCallFindById(String productinventoryName) {
        ProductInventory productinventory = repository.save(new ProductInventory(productinventoryName));
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        assertNotNull(oProductInventory.get(), "Should return ProductInventory is not null");
        assertNotNull(oProductInventory.get().getId(), "Should return ProductInventory ID is not null");
        assertNotNull(oProductInventory.get().getName(), "Should return ProductInventory NAME is not null");
    }

  
    @Order(3)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldWhenCallUpdate(String productinventoryName) {
        String productinventoryNameUpdate = "Test-Update-ProductInventory";
        ProductInventory productinventory = repository.save(new ProductInventory(productinventoryName));
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        ProductInventory newProductInventory = oProductInventory.get();
        newProductInventory.setName(productinventoryNameUpdate);
        newProductInventory = repository.save(newProductInventory);
        assertEquals(productinventoryNameUpdate, newProductInventory.getName());
    }
  
    @Order(4)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldWhenCallDelete(String productinventoryName) {
        ProductInventory productinventory = repository.save(new ProductInventory(productinventoryName));
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        repository.delete(oProductInventory.get());
        Optional<ProductInventory> findProductInventory = repository.findById(oProductInventory.get().getId());
        assertFalse(findProductInventory.isPresent());
    }
}
