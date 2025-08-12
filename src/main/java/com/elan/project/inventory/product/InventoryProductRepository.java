package com.elan.project.inventory.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryProductRepository extends JpaRepository<InventoryProductEntity, Integer>{

}
