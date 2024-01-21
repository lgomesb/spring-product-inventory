package com.barbosa.ms.productinventory.productinventory.repositories.failed;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.barbosa.ms.productinventory.productinventory.ProductInventoryApplicationTests;
import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;

import jakarta.validation.ConstraintViolationException;

@ActiveProfiles(value = "test")
@DataJpaTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = ProductInventoryApplicationTests.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductInventoryRepositoryFailedTest {

    @Autowired
    private ProductInventoryRepository repository;

    private static Stream<Arguments> provideProductInventoryData() {        
        return Stream.of(
          Arguments.of("ProductInventory-Test-01"),
          Arguments.of("ProductInventory-Test-02")
        );
    }

    
    @BeforeAll
    void shouldSuccessfulInjectComponent() {
        assertNotNull(repository);
    }

    @Order(0)
    @Test()
    @DisplayName("Should return Exception when ProductInventory not null")
    void shouldFailWhenCallCreate() {
        assertThrows(ConstraintViolationException.class, () -> {
            repository.saveAndFlush(new ProductInventory(null));
        }, "Should return Error when ProductInventory not null");
    }


    @Order(1)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldFailWhenCallFindById(String productinventoryName) {
        repository.save(new ProductInventory(productinventoryName));
        Optional<ProductInventory> oProductInventory = repository.findById(UUID.randomUUID());
        assertThrows( ObjectNotFoundException.class, () -> {
            oProductInventory.orElseThrow(() ->
                 new ObjectNotFoundException("ProductInventory", UUID.randomUUID()));
        });
    }

  
    @Order(2)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldFailWhenCallUpdate(String productinventoryName) {
        String productinventoryNameUpdate = "";
        ProductInventory productinventory = repository.save(new ProductInventory(productinventoryName));
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());        
        assertThrows(ConstraintViolationException.class, () -> {
            ProductInventory newProductInventory = oProductInventory.get();
            newProductInventory.setName(productinventoryNameUpdate);
            repository.saveAndFlush(newProductInventory);
        }, "Should return Error when ProductInventory not blank or empty");
    }
  
    @Order(3)
    @ParameterizedTest
    @MethodSource("provideProductInventoryData")
    void shouldFailWhenCallDelete(String productinventoryName) {
        ProductInventory productinventory = new ProductInventory(UUID.randomUUID(), productinventoryName);
        Optional<ProductInventory> oProductInventory = repository.findById(productinventory.getId());
        assertThrows( InvalidDataAccessApiUsageException.class, () -> {
            repository.delete(oProductInventory.orElse(null));
        }, "Should return Error when ProductInventory not blank or empty");
    }
}
