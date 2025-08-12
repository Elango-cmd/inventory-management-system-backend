package com.elan.project.inventory.supplier;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/elan/inventory/v1")
@Tag(name = "Inventory Supplier Application", description = "Spring Boot Backend for Inventory Supplier Management")
public class InventorySupplierController {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private InventorySupplierService inventorySupplierService;

    // GET all suppliers
    @GetMapping(value = "/inventory/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "View all inventory supplier details")
    public List<InventorySupplierDTO> getInventorySupplierDetails() {
        return inventorySupplierService.findInventorySupplierDetails();
    }

    // GET supplier by ID
    @GetMapping(value = "/inventory/supplier/supplierId", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "View inventory supplier detail by supplier ID")
    public ResponseEntity<Object> getInventorySupplierDetailById(
            @Parameter(description = "Get inventory supplier detail by supplier ID") @RequestParam Integer supplierId) {
        return inventorySupplierService.findInventorySupplierDetailBySupplierId(supplierId);
    }

    // POST create supplier
    @PostMapping(value = "/inventory/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create inventory supplier detail")
    public ResponseEntity<Object> createInventorySupplierDetail(
            @Parameter(description = "Create inventory supplier detail") @RequestBody InventorySupplierDTO inventorySupplierDTO) {
        return inventorySupplierService.createInventorySupplierDetail(inventorySupplierDTO);
    }

    // PUT update supplier
    @PutMapping(value = "/inventory/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Update inventory supplier detail by supplier ID")
    public ResponseEntity<Object> updateInventorySupplierDetail(
            @Parameter(description = "Update inventory supplier detail") @RequestBody InventorySupplierDTO inventorySupplierDTO,
            @Parameter(description = "Supplier ID to update") @RequestParam Integer supplierId) {
        return inventorySupplierService.updateInventorySupplierDetail(inventorySupplierDTO, supplierId);
    }

    // DELETE supplier
    @DeleteMapping(value = "/inventory/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Delete inventory supplier detail by supplier ID")
    public ResponseEntity<String> deleteInventorySupplierDetailById(
            @Parameter(description = "Delete inventory supplier detail by supplier ID") @RequestParam Integer supplierId) {
        return inventorySupplierService.deleteInventorySupplierDetailBySupplierId(supplierId);
    }
}

