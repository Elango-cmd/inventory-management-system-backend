package com.elan.project.inventory.supplier;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface InventorySupplierService {

	List<InventorySupplierDTO> findInventorySupplierDetails();

	ResponseEntity<Object> findInventorySupplierDetailBySupplierId(Integer supplierId);

	ResponseEntity<Object> createInventorySupplierDetail(InventorySupplierDTO inventorySupplierDTO);

	ResponseEntity<Object> updateInventorySupplierDetail(InventorySupplierDTO inventorySupplierDTO, Integer supplierId);

	ResponseEntity<String> deleteInventorySupplierDetailBySupplierId(Integer supplierId);

}
