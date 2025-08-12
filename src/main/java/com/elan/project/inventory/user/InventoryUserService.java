package com.elan.project.inventory.user;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface InventoryUserService {

	List<InventoryUserDTO> findInventoryUserDetail();

	ResponseEntity<Object> findInventoryUserDetailByUserId(Integer userId);

	ResponseEntity<Object> createInventotryUserDetail(InventoryUserDTO inventoryUserDTO);

	ResponseEntity<Object> updateInventoryUserDetail(InventoryUserDTO inventoryUserDTO, Integer userId);

	ResponseEntity<String> deleteInventoryUserDetailByUserId(Integer userId);

	ResponseEntity<Object> loginInventoryUser(String emailId, String password);

}
