package com.elan.project.inventory.supplier;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventorySupplierRepository extends JpaRepository<InventorySupplierEntity, Integer>{

	Optional<InventorySupplierEntity> findByEmail(String email);

	Optional<InventorySupplierEntity> findBysupplierId(Long supplierId);

}
