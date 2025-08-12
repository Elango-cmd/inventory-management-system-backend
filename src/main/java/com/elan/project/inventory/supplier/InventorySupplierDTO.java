package com.elan.project.inventory.supplier;

import lombok.Data;

@Data
public class InventorySupplierDTO {

    private Long supplierId;

    private String name;

    private String email;

    private String phone;

    private String address;
}

