package com.elan.project.inventory.user;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/elan/inventory/v1")
@Tag(name = "Inventory Application", description = "Spring Boot Backend for Inventory Application")
public class InventoryUserController {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private InventoryUserService inventoryUserService;
	
	@GetMapping(value = "/inventory/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "view all inventory user detail")
	public List<InventoryUserDTO> getInventoryUserDetail() {
		return inventoryUserService.findInventoryUserDetail();
	}

	@GetMapping(value = "/inventory/user/userId", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "view the inventory user detail by user id")
	public ResponseEntity<Object> getInventoryUserDetailByUserId(
			@Parameter(description ="get inventory user detail by user id ") @RequestParam Integer userId) {
		return inventoryUserService.findInventoryUserDetailByUserId(userId);
	}

	@PostMapping(value = "/inventory/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "create the inventory user detail")
	public ResponseEntity<Object> createInventoryUserDetail(
			@Parameter(description ="create the inventory user detail") @RequestBody InventoryUserDTO inventoryUserDTO) {
		return inventoryUserService.createInventotryUserDetail(inventoryUserDTO);
	}

	@PutMapping(value = "/inventory/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Update the inventory user detail by user id")
	public ResponseEntity<Object> updateInventoryUserDetail(
			@Parameter(description ="update the inventory user detail") @RequestBody InventoryUserDTO inventoryUserDTO,
			@Parameter(description ="update inventory user detail by user id ") @RequestParam Integer userId) {
		return inventoryUserService.updateInventoryUserDetail(inventoryUserDTO, userId);
	}

	@DeleteMapping(value = "/inventory/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "delete inventory user detail by user id")
	public ResponseEntity<String> deleteInventoryUserDetailByUserId(
			@Parameter(description ="delete inventory user detail by user id ") @RequestParam Integer userId) {
		return inventoryUserService.deleteInventoryUserDetailByUserId(userId);
	}
	
	@PostMapping(value = "/inventory/user/loginUser", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Login inventory user with email and password")
	public ResponseEntity<Object> loginInventoryUser(@RequestBody LoginRequest loginRequest) {
	    return inventoryUserService.loginInventoryUser(loginRequest.getEmailId(), loginRequest.getPassword());
	}

}
