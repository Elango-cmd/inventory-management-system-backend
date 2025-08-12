package com.elan.project.inventory.product;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface InventoryProductService {

	List<InventoryProductDTO> findInventoryProductDetail();

	ResponseEntity<Object> findInventoryProductDetailByProductId(Integer productId);

	ResponseEntity<Object> createInventotryProductDetail(InventoryProductDTO inventoryProductDTO);

	ResponseEntity<Object> updateInventoryProductDetail(InventoryProductDTO inventoryProductDTO, Integer productId);

	ResponseEntity<String> deleteInventoryProductDetailByproductId(Integer productId);

}
