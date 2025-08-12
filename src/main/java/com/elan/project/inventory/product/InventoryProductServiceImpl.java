package com.elan.project.inventory.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.elan.project.inventory.supplier.InventorySupplierDTO;
import com.elan.project.inventory.supplier.InventorySupplierEntity;
import com.elan.project.inventory.supplier.InventorySupplierRepository;

@Service
public class InventoryProductServiceImpl implements InventoryProductService{
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private InventoryProductRepository inventoryProductRepository;
	
	@Autowired
	private InventorySupplierRepository inventorySupplierRepository;

	@Override
	public List<InventoryProductDTO> findInventoryProductDetail() {
		 List<InventoryProductEntity> entities = inventoryProductRepository.findAll();
		    return entities.stream().map(this::inventoryProductEntityToDTO).collect(Collectors.toList());
	}
	
	private InventoryProductDTO inventoryProductEntityToDTO(InventoryProductEntity productEntity) {
	    InventoryProductDTO dto = new InventoryProductDTO();
	    dto.setProductId(productEntity.getProductId());
	    dto.setName(productEntity.getName());
	    dto.setDescription(productEntity.getDescription());
	    dto.setQuantity(productEntity.getQuantity());
	    dto.setPrice(productEntity.getPrice());
	    dto.setCategory(productEntity.getCategory());

	    // Convert Supplier Entity to DTO
	    InventorySupplierDTO supplierDTO = new InventorySupplierDTO();
	    supplierDTO.setSupplierId(productEntity.getSupplier().getSupplierId());
	    supplierDTO.setName(productEntity.getSupplier().getName());
	    // Add more fields as needed

	    dto.setSupplier(supplierDTO);
	    return dto;
	}


	@Override
	public ResponseEntity<Object> findInventoryProductDetailByProductId(Integer productId) {
		 try {
		        Optional<InventoryProductEntity> productOpt = inventoryProductRepository.findById(productId);
		        if (productOpt.isPresent()) {
		            return ResponseEntity.ok(inventoryProductEntityToDTO(productOpt.get()));
		        } else {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                    .body("Product not found with ID: " + productId);
		        }
		    } catch (Exception e) {
		        log.error("Error retrieving product by ID: {}", productId, e);
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Error fetching product details.");
		    }
	}

	@Override
	public ResponseEntity<Object> createInventotryProductDetail(InventoryProductDTO inventoryProductDTO) {
	    try {
	        InventoryProductEntity product = new InventoryProductEntity();
	        product.setName(inventoryProductDTO.getName());
	        product.setDescription(inventoryProductDTO.getDescription());
	        product.setQuantity(inventoryProductDTO.getQuantity());
	        product.setPrice(inventoryProductDTO.getPrice());
	        product.setCategory(inventoryProductDTO.getCategory());

	        // Fetch Supplier Entity from DB using ID
	        InventorySupplierEntity supplier = inventorySupplierRepository
	                .findBysupplierId(inventoryProductDTO.getSupplier().getSupplierId())
	                .orElseThrow(() -> new RuntimeException("Supplier not found"));
	        product.setSupplier(supplier);

	        InventoryProductEntity saved = inventoryProductRepository.save(product);
	        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryProductEntityToDTO(saved));
	    } catch (Exception e) {
	        log.error("Error creating product", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error creating product. Please try again later.");
	    }
	}

	@Override
	public ResponseEntity<Object> updateInventoryProductDetail(InventoryProductDTO inventoryProductDTO,
			Integer productId) {
		 return inventoryProductRepository.findById(productId).map(product -> {
		        product.setName(inventoryProductDTO.getName());
		        product.setDescription(inventoryProductDTO.getDescription());
		        product.setQuantity(inventoryProductDTO.getQuantity());
		        product.setPrice(inventoryProductDTO.getPrice());
		        product.setCategory(inventoryProductDTO.getCategory());

		        // Update Supplier if needed
		        InventorySupplierEntity supplier = inventorySupplierRepository
		                .findBysupplierId(inventoryProductDTO.getSupplier().getSupplierId())
		                .orElseThrow(() -> new RuntimeException("Supplier not found"));
		        product.setSupplier(supplier);

		        InventoryProductEntity updated = inventoryProductRepository.save(product);
		        return ResponseEntity.ok((Object)inventoryProductEntityToDTO(updated));
		    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
		            .body("Product not found with ID: " + productId));
	}

	@Override
	public ResponseEntity<String> deleteInventoryProductDetailByproductId(Integer productId) {
	    return inventoryProductRepository.findById(productId).map(product -> {
	        inventoryProductRepository.delete(product);
	        return ResponseEntity.ok("Product deleted successfully.");
	    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body("Product not found with ID: " + productId));
	}

}
