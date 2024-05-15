package com.barbosa.ms.productinventory.productinventory.services.failed;

import com.barbosa.ms.productinventory.productinventory.domain.entities.ProductInventory;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.repositories.ProductInventoryRepository;
import com.barbosa.ms.productinventory.productinventory.services.impl.ProductInventoryServiceImpl;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ProductInventoryServiceFailedTest {

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
    void shouldFailWhenCreate() {
        given.productinventoryInicietedForFailueReturn();
        given.productinventoryRecordInicietedForFailueReturn();
        when.saveProductInventoryEntity();
        then.shouldBeFailueWhenCreateProductInventory(DataIntegrityViolationException.class);
    }

    @Test
    void shouldFailWhenFindById() {
        given.productinventoryInicietedForFailueReturn();
        when.findProductInventoryByIdWithFail();        
        then.shouldBeFailueWhenFindProductInventoryById(ObjectNotFoundException.class);
    }

    @Test
    void shouldFailWhenUpdateWithIdNonExistent() {
        given.productinventoryInicietedForFailueReturn();
        given.productinventoryRecordInicietedForFailueReturn();
        when.findProductInventoryByIdWithFail();        
        then.shouldBeFailueWhenFindProductInventoryById(ObjectNotFoundException.class);    
    }

    @Test
    void shouldFailWhenUpdateWithInvalidArgument() {
        given.productinventoryInicietedForFailueReturn();
        given.productinventoryRecordInicietedForFailueReturn();
        when.findProductInventoryById();
        when.saveProductInventoryEntity();
        then.shouldBeFailueWhenUpdateProductInventory(DataIntegrityViolationException.class);        
    }

    @Test
    void shouldFailWhenDelete() {
        given.productinventoryInicietedForFailueReturn();
        when.findProductInventoryByIdWithFail();
        then.shouldBeFailueWhenDeleteProductInventory(ObjectNotFoundException.class);     
    }

    class Given {

        public UUID creationIdOfProductInventory() {
            return UUID.randomUUID();
        }

        void productinventoryInicietedForFailueReturn() {
           productinventory = ProductInventory.builder()
                        .id(creationIdOfProductInventory())
                        .productId(null)
                        .build();
        }

        void productinventoryRecordInicietedForFailueReturn () {
            productinventoryRecord = new ProductInventoryRecord(productinventory.getId(), null, 0);
        }
    }

    class When {
        
        public ProductInventoryRecord callCreateInProductInventorySerivce() {
            return service.create(productinventoryRecord);
        }
        
        public ProductInventoryRecord callProductInventoryServiceFindById() {
            return service.findById(given.creationIdOfProductInventory());
        }

        void callProductInventorySerivceUpdate() {
            service.update(productinventoryRecord);
        }

        void callDelteInProductInventorySerivce() {
            service.delete(given.creationIdOfProductInventory());
        }

        void saveProductInventoryEntity() {            
            doThrow(new DataIntegrityViolationException("Error inserting productinventory"))
                .when(repository)
                .save(any(ProductInventory.class));
        }

        void findProductInventoryById() {
            when(repository.findById(any(UUID.class))).thenReturn(Optional.of(productinventory));
        }

        void deleteProductInventoryEntity() {
            doNothing().when(repository).delete(any(ProductInventory.class));
        }

        void findProductInventoryByIdWithFail() {
            doThrow(new ObjectNotFoundException("ProductInventory", given.creationIdOfProductInventory()))
                .when(repository).findById(any(UUID.class));
                
        }

    }
    
    class Then {

        public <T extends Throwable> void shouldBeFailueWhenCreateProductInventory(Class<T> clazz) {
           assertThrows(clazz, () -> {
                when.callCreateInProductInventorySerivce();
           });
        }

        
        public <T extends Throwable> void shouldBeFailueWhenFindProductInventoryById(Class<T> clazz) {
            assertThrows(clazz, () -> {
                when.callProductInventoryServiceFindById();
            });
        }
        
        public <T extends Throwable> void shouldBeFailueWhenUpdateProductInventory(Class<T> clazz) {
            assertThrows(clazz, () -> {
                when.callProductInventorySerivceUpdate();
            });
        }

        public <T extends Throwable> void shouldBeFailueWhenDeleteProductInventory(Class<T> clazz) {
            assertThrows(clazz, () -> {
                when.callDelteInProductInventorySerivce();
            });
        }
    }
}
