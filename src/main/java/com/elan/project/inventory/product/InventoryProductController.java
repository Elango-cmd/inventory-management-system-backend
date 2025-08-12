package com.elan.project.inventory.product;

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
@Tag(name = "Inventory Product Application", description = "Spring Boot Backend for Inventory Application")
public class InventoryProductController {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private InventoryProductService inventoryProductService;


	@GetMapping(value = "/inventory/product", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "view all inventory product detail")
	public List<InventoryProductDTO> getInventoryProductDetail() {
		return inventoryProductService.findInventoryProductDetail();
	}

	@GetMapping(value = "/inventory/product/productId", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "view the inventory product detail by product id")
	public ResponseEntity<Object> getInventoryProductDetailByUserId(
			@Parameter(description ="get inventory product detail by product id ") @RequestParam Integer productId) {
		return inventoryProductService.findInventoryProductDetailByProductId(productId);
	}

	@PostMapping(value = "/inventory/product", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "create the inventory product detail")
	public ResponseEntity<Object> createInventoryProductDetail(
			@Parameter(description ="create the inventory product detail") @RequestBody InventoryProductDTO inventoryProductDTO) {
		return inventoryProductService.createInventotryProductDetail(inventoryProductDTO);
	}

	@PutMapping(value = "/inventory/product", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Update the inventory product detail by product id")
	public ResponseEntity<Object> updateInventoryProductDetail(
			@Parameter(description ="update the inventory product detail") @RequestBody InventoryProductDTO inventoryProductDTO,
			@Parameter(description ="update inventory product detail by product id ") @RequestParam Integer productId) {
		return inventoryProductService.updateInventoryProductDetail(inventoryProductDTO, productId);
	}

	@DeleteMapping(value = "/inventory/product", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "delete inventory product detail by product id")
	public ResponseEntity<String> deleteInventoryProductDetailByProductId(
			@Parameter(description ="delete inventory product detail by product id ") @RequestParam Integer productId) {
		return inventoryProductService.deleteInventoryProductDetailByproductId(productId);
	}
}
