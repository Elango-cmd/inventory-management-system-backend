package com.elan.project.inventory.product;

import com.elan.project.inventory.supplier.InventorySupplierDTO;
import lombok.Data;

@Data
public class InventoryProductDTO {

	private Integer productId;

	private String name;

	private String description;

	private Integer quantity;

	private Double price;

	private String category;

    private InventorySupplierDTO supplier;
}
