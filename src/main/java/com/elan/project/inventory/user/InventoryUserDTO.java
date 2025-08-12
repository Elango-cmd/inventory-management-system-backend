package com.elan.project.inventory.user;

import lombok.Data;

@Data
public class InventoryUserDTO {

	private Integer userId;

	private String userName;

	private String password;

	private String emailId;

	private String role;
}
