package com.barbosa.ms.productinventory.productinventory.services.succeed;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import com.barbosa.ms.productinventory.productinventory.services.impl.ProductInventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductInventoryServiceSucceedTest {

    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final Integer QUANTITY = 1;
    @InjectMocks
    private ProductInventoryServiceImpl service;

    @Mock
    private ProductInventoryRepository repository;
    
    private ProductInventory productinventory;
    private ProductInventoryRecord productinventoryRecord;
    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();


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
                        .productId(PRODUCT_ID)
                        .quantity(QUANTITY)
                        .build();
        }

        void productinventoryRecordInicietedForSuccessfulReturn () {
            productinventoryRecord = new ProductInventoryRecord(productinventory.getId(), PRODUCT_ID, QUANTITY);
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
            assertNotNull(record.productId());
            assertEquals(record.productId(), productinventory.getProductId());
            assertNotNull(record.id());
            assertEquals(record.id(), productinventory.getId());
        }

        void shouldBeSuccessfulArgumentValidationByDelete() {
            ArgumentCaptor<ProductInventory> productinventoryCaptor = ArgumentCaptor.forClass(ProductInventory.class);
            verify(repository).delete(productinventoryCaptor.capture());
            assertNotNull(productinventoryCaptor.getValue());
            assertEquals(productinventoryCaptor.getValue().getProductId(),productinventory.getProductId());
        }

        void shouldBeSuccessfulArgumentValidationByUpdate() {
            ArgumentCaptor<ProductInventory> productinventoryCaptor = ArgumentCaptor.forClass(ProductInventory.class);
            verify(repository).save(productinventoryCaptor.capture());
            assertNotNull(productinventoryCaptor.getValue());
            assertEquals(productinventoryCaptor.getValue().getProductId(),productinventory.getProductId());
        }

        void shouldBeSuccessfulArgumentValidationByListAll(List<ProductInventoryRecord> productinventoryRecords) {
            assertNotNull(productinventoryRecords);
            assertFalse(productinventoryRecords.isEmpty());
        }
    }
}
