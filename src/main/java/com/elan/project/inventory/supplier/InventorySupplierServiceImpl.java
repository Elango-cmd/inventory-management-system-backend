package com.elan.project.inventory.supplier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InventorySupplierServiceImpl implements InventorySupplierService {

    private static final Logger log = LoggerFactory.getLogger(InventorySupplierServiceImpl.class);

    @Autowired
    private InventorySupplierRepository inventorySupplierRepository;

    @Override
    public List<InventorySupplierDTO> findInventorySupplierDetails() {
        List<InventorySupplierEntity> entities = inventorySupplierRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> findInventorySupplierDetailBySupplierId(Integer supplierId) {
        try {
            Optional<InventorySupplierEntity> optional = inventorySupplierRepository.findById(supplierId);
            if (optional.isPresent()) {
                return ResponseEntity.ok(convertEntityToDTO(optional.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Supplier not found with ID: " + supplierId);
            }
        } catch (Exception e) {
            log.error("Error retrieving supplier with ID: {}", supplierId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching supplier details. Please try again.");
        }
    }

    @Override
    public ResponseEntity<Object> createInventorySupplierDetail(InventorySupplierDTO dto) {
        try {
            Optional<InventorySupplierEntity> existing = inventorySupplierRepository.findByEmail(dto.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Supplier with this email already exists.");
            }

            InventorySupplierEntity entity = convertDTOToEntity(dto);
            InventorySupplierEntity saved = inventorySupplierRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertEntityToDTO(saved));

        } catch (Exception e) {
            log.error("Error creating supplier", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating supplier. Please try again.");
        }
    }

    @Override
    public ResponseEntity<Object> updateInventorySupplierDetail(InventorySupplierDTO dto, Integer supplierId) {
        return inventorySupplierRepository.findById(supplierId).map(existing -> {
            existing.setName(dto.getName());
            existing.setEmail(dto.getEmail());
            existing.setPhone(dto.getPhone());
            existing.setAddress(dto.getAddress());

            InventorySupplierEntity updated = inventorySupplierRepository.save(existing);
            return ResponseEntity.ok((Object)convertEntityToDTO(updated));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Supplier not found with ID: " + supplierId));
    }

    @Override
    public ResponseEntity<String> deleteInventorySupplierDetailBySupplierId(Integer supplierId) {
        return inventorySupplierRepository.findById(supplierId).map(existing -> {
            inventorySupplierRepository.delete(existing);
            return ResponseEntity.ok("Supplier deleted successfully.");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Supplier not found with ID: " + supplierId));
    }

    // Entity → DTO
    private InventorySupplierDTO convertEntityToDTO(InventorySupplierEntity entity) {
        InventorySupplierDTO dto = new InventorySupplierDTO();
        dto.setSupplierId(entity.getSupplierId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        return dto;
    }

    // DTO → Entity
    private InventorySupplierEntity convertDTOToEntity(InventorySupplierDTO dto) {
        InventorySupplierEntity entity = new InventorySupplierEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}

