package com.barbosa.ms.productinventory.productinventory.repositories.failed;

import com.barbosa.ms.productinventory.productinventory.ProductInventoryApplicationTests;
import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

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
    private static final UUID PRODUCT_ORDER_ID = UUID.randomUUID();

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
        ProductInventory productInventory = this.getNewProductInventory();
        productInventory.setProductId(null);
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(productInventory),
                "Should return Error when ProductInventory not null");
    }

    @Test
    void shouldThrowIntegrityViolationErrorWhenCreate() {
        ProductInventory newProductInventory = repository.saveAndFlush(ProductInventory.builder()
                .productId(UUID.randomUUID())
                .productOrderId(UUID.randomUUID())
                .quantity(10)
                .build());

        ProductInventory otherProductInventory = ProductInventory.builder()
                .productId(newProductInventory.getProductId())
                .productOrderId(newProductInventory.getProductOrderId())
                .quantity(20)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(otherProductInventory),
                "Should return error integrity violation");
    }

    @Order(1)
    @Test
    void shouldFailWhenCallFindById() {
        repository.save(getNewProductInventory());
        Optional<ProductInventory> oProductInventory = repository.findById(UUID.randomUUID());
        assertThrows( ObjectNotFoundException.class, () -> {
            oProductInventory.orElseThrow(() ->
                 new ObjectNotFoundException("ProductInventory", UUID.randomUUID()));
        });
    }

  
    @Order(2)
    @Test
    void shouldFailWhenCallUpdate() {
        ProductInventory productinventory = repository.save(getNewProductInventory());
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        ProductInventory newProductInventory = oProductInventory.orElseThrow();
        newProductInventory.setProductOrderId(null);
        newProductInventory.setQuantity(2);
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(newProductInventory),
                "Should return Error when ProductInventory not blank or empty");
    }
  
    @Order(3)
    @Test
    void shouldFailWhenCallDelete() {
        ProductInventory productInventory = null;
        assertThrows( InvalidDataAccessApiUsageException.class, () -> repository.delete(productInventory),
                "Should return Error when ProductInventory not blank or empty");
    }

    private ProductInventory getNewProductInventory() {
        return new ProductInventory(null, PRODUCT_ID, PRODUCT_ORDER_ID, QUANTITY);
    }
}
