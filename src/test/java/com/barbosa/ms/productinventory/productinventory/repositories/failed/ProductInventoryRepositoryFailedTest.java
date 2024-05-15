package com.barbosa.ms.productinventory.productinventory.repositories.failed;

import com.barbosa.ms.productinventory.productinventory.ProductInventoryApplicationTests;
import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(value = "test")
@DataJpaTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = ProductInventoryApplicationTests.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductInventoryRepositoryFailedTest {

    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final Integer QUANTITY = 1;

    @Autowired
    private ProductInventoryRepository repository;


    @BeforeAll
    void shouldSuccessfulInjectComponent() {
        assertNotNull(repository);
    }

    @Order(0)
    @Test()
    @DisplayName("Should return Exception when ProductInventory not null")
    void shouldFailWhenCallCreate() {
        assertThrows(ConstraintViolationException.class, () -> {
            repository.saveAndFlush(new ProductInventory(null, PRODUCT_ID, QUANTITY));
        }, "Should return Error when ProductInventory not null");
    }


    @Order(1)
    @Test
    void shouldFailWhenCallFindById() {
        repository.save(new ProductInventory(null, PRODUCT_ID, QUANTITY));
        Optional<ProductInventory> oProductInventory = repository.findById(UUID.randomUUID());
        assertThrows( ObjectNotFoundException.class, () -> {
            oProductInventory.orElseThrow(() ->
                 new ObjectNotFoundException("ProductInventory", UUID.randomUUID()));
        });
    }

  
    @Order(2)
    @Test
    void shouldFailWhenCallUpdate() {
        ProductInventory productinventory = repository.save(new ProductInventory(null, PRODUCT_ID, QUANTITY));
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());        
        assertThrows(ConstraintViolationException.class, () -> {
            ProductInventory newProductInventory = oProductInventory.get();
            newProductInventory.setProductId(UUID.randomUUID());
            newProductInventory.setQuantity(2);
            repository.saveAndFlush(newProductInventory);
        }, "Should return Error when ProductInventory not blank or empty");
    }
  
    @Order(3)
    @Test
    void shouldFailWhenCallDelete() {
        ProductInventory productinventory = new ProductInventory(UUID.randomUUID(), PRODUCT_ID, QUANTITY);
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        assertThrows( InvalidDataAccessApiUsageException.class, () -> {
            repository.delete(oProductInventory.orElse(null));
        }, "Should return Error when ProductInventory not blank or empty");
    }
}
