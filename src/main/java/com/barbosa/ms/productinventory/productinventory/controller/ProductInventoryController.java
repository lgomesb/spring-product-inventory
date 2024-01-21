package com.barbosa.ms.productinventory.productinventory.controller;

import com.barbosa.ms.productinventory.productinventory.domain.dto.ProductInventoryRequestDTO;
import com.barbosa.ms.productinventory.productinventory.domain.dto.ProductInventoryResponseDTO;
import com.barbosa.ms.productinventory.productinventory.domain.records.ProductInventoryRecord;
import com.barbosa.ms.productinventory.productinventory.services.ProductInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "ProductInventory", description = "Endpoints for ProductInventory operations")
@RestController
@RequestMapping("/")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService service;

    @Operation(summary = "Create ProductInventory", description = "Create a new ProductInventory", tags = { "ProductInventory" })
    @PostMapping
    public ResponseEntity<ProductInventoryResponseDTO> create(@RequestBody @Valid ProductInventoryRequestDTO dto) {

        ProductInventoryRecord productinventoryRecord = service.create(new ProductInventoryRecord(null, dto.getName()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(productinventoryRecord.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find ProductInventory by Id", description = "Find ProductInventory by id", tags = { "ProductInventory" })
    @GetMapping("{id}")
    public ResponseEntity<ProductInventoryResponseDTO> findById(@PathVariable("id") String id) {
        ProductInventoryRecord productinventoryRecord = service.findById(UUID.fromString(id));
        return ResponseEntity.ok().body(ProductInventoryResponseDTO.builder()
                .id(productinventoryRecord.id())
                .name(productinventoryRecord.name())
                .build());
    }

    @Operation(summary = "Update ProductInventory by Id", description = "Update ProductInventory by id", tags = { "ProductInventory" })
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody ProductInventoryRequestDTO dto) {
        service.update(new ProductInventoryRecord(UUID.fromString(id), dto.getName()));
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Delete ProductInventory by Id", description = "Delete ProductInventory by id", tags = { "ProductInventory" })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List all ProductInventory", description = "List all ProductInventory in the database", tags = {"ProductInventory"})
    @GetMapping()
    public ResponseEntity<List<ProductInventoryResponseDTO>> listAll() {
        List<ProductInventoryResponseDTO> objects = service.listAll()
                .stream()
                .map(ProductInventoryResponseDTO::create)
                .toList();

        return ResponseEntity.ok(objects);
    }

}
