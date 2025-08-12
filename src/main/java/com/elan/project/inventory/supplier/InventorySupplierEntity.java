package com.elan.project.inventory.supplier;

import jakarta.persistence.*; // or use javax.persistence.* depending on your setup
import lombok.Data;

@Entity
@Table(name = "ev_supplier_detail")
@Data
public class InventorySupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
}

