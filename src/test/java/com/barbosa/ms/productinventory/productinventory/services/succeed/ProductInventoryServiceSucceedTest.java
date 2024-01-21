package com.barbosa.ms.productinventory.productinventory.services.succeed;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import com.barbosa.ms.productinventory.productinventory.services.impl.ProductInventoryServiceImpl;


class ProductInventoryServiceSucceedTest {

    @InjectMocks
    private ProductInventoryServiceImpl service;

    @Mock
    private ProductInventoryRepository repository;
    
    private ProductInventory productinventory;
    private ProductInventoryRecord productinventoryRecord;
    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSuccessWhenCreate() {
        given.productinventoryInicietedForSuccessfulReturn();
        given.productinventoryRecordInicietedForSuccessfulReturn();
        when.saveProductInventoryEntity();
        ProductInventoryRecord record = when.callCreateInProductInventorySerivce();
        then.shouldBeSuccessfulValidationRules(record);
    }

    @Test
    void shouldSuccessWhenFindById() {
        given.productinventoryInicietedForSuccessfulReturn();
        when.findProductInventoryById();
        ProductInventoryRecord record = when.callProductInventoryServiceFindById();
        then.shouldBeSuccessfulValidationRules(record);
    }

    @Test
    void shouldSuccessWhenUpdate() {
        given.productinventoryInicietedForSuccessfulReturn();
        given.productinventoryRecordInicietedForSuccessfulReturn();
        when.findProductInventoryById();
        when.callProductInventoryServiceFindById();
        when.saveProductInventoryEntity();
        when.callProductInventorySerivceUpdate();
        then.shouldBeSuccessfulArgumentValidationByUpdate();        
    }

    @Test
    void shouldSuccessWhenDelete() {
        given.productinventoryInicietedForSuccessfulReturn();
        when.findProductInventoryById();
        when.deleteProductInventoryEntity();
        when.callDelteInProductInventorySerivce();    
        then.shouldBeSuccessfulArgumentValidationByDelete();    
    }

    @Test
    void shouldSuccessWhenListAll() {
        given.productinventoryInicietedForSuccessfulReturn();
        when.findAllProductInventory();
        List<ProductInventoryRecord>  productinventoryRecords = when.callListAllInProductInventoryService();
        then.shouldBeSuccessfulArgumentValidationByListAll(productinventoryRecords);
    }

    class Given {

        public UUID creationIdOfProductInventory() {
            return UUID.randomUUID();
        }

        void productinventoryInicietedForSuccessfulReturn() {
           productinventory = ProductInventory.builder()
                        .id(creationIdOfProductInventory())
                        .name("ProductInventory-Test-Success")
                        .build();
        }

        void productinventoryRecordInicietedForSuccessfulReturn () {
            productinventoryRecord = new ProductInventoryRecord(productinventory.getId(), productinventory.getName());
        }
    }

    class When {

        void saveProductInventoryEntity() {
            when(repository.save(any(ProductInventory.class)))
            .thenReturn(productinventory);
        }

        void callProductInventorySerivceUpdate() {
            service.update(productinventoryRecord);
        }

        void callDelteInProductInventorySerivce() {
            service.delete(given.creationIdOfProductInventory());
        }

        void deleteProductInventoryEntity() {
            doNothing().when(repository).delete(any(ProductInventory.class));
        }

        public ProductInventoryRecord callProductInventoryServiceFindById() {
            return service.findById(given.creationIdOfProductInventory());
        }

        void findProductInventoryById() {
            when(repository.findById(any(UUID.class))).thenReturn(Optional.of(productinventory));
        }

        public ProductInventoryRecord callCreateInProductInventorySerivce() {
            return service.create(productinventoryRecord);
        }

        void findAllProductInventory() {
            when(repository.findAll()).thenReturn(Collections.singletonList(productinventory));
        }

        public List<ProductInventoryRecord> callListAllInProductInventoryService() {
            return service.listAll();
        }
    }
    
    class Then {

        void shouldBeSuccessfulValidationRules(ProductInventoryRecord record) {
            assertNotNull(record);
            assertNotNull(record.name());
            assertEquals(record.name(), productinventory.getName());
            assertNotNull(record.id());
            assertEquals(record.id(), productinventory.getId());
        }

        void shouldBeSuccessfulArgumentValidationByDelete() {
            ArgumentCaptor<ProductInventory> productinventoryCaptor = ArgumentCaptor.forClass(ProductInventory.class);
            verify(repository).delete(productinventoryCaptor.capture());
            assertNotNull(productinventoryCaptor.getValue());
            assertEquals(productinventoryCaptor.getValue().getName(),productinventory.getName());
        }

        void shouldBeSuccessfulArgumentValidationByUpdate() {
            ArgumentCaptor<ProductInventory> productinventoryCaptor = ArgumentCaptor.forClass(ProductInventory.class);
            verify(repository).save(productinventoryCaptor.capture());
            assertNotNull(productinventoryCaptor.getValue());
            assertEquals(productinventoryCaptor.getValue().getName(),productinventory.getName());
        }

        void shouldBeSuccessfulArgumentValidationByListAll(List<ProductInventoryRecord> productinventoryRecords) {
            assertNotNull(productinventoryRecords);
            assertFalse(productinventoryRecords.isEmpty());
        }
    }
}
