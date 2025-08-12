package com.elan.project.inventory.user;

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
public class InventoryUserServiceImpl implements InventoryUserService {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private InventoryUserRepository inventoryUserRepository;

	@Override
	public List<InventoryUserDTO> findInventoryUserDetail() {
		List<InventoryUserEntity> inventoryUserAuthEntities = inventoryUserRepository.findAll();
		List<InventoryUserDTO> inventoryUserAuthDTOs = inventoryUserAuthEntities.stream()
				.map(this::inventoryUserAuthenticationEntityToDTO).collect(Collectors.toList());
		return inventoryUserAuthDTOs;
	}

	private InventoryUserDTO inventoryUserAuthenticationEntityToDTO(InventoryUserEntity inventoryUserEntity) {
		InventoryUserDTO inventoryUserDTO = new InventoryUserDTO();
		inventoryUserDTO.setUserId(inventoryUserEntity.getUserId());
		inventoryUserDTO.setUserName(inventoryUserEntity.getUserName());
		inventoryUserDTO.setEmailId(inventoryUserEntity.getEmailId());
		inventoryUserDTO.setPassword(inventoryUserEntity.getPassword());
		inventoryUserDTO.setRole(inventoryUserEntity.getRole());
		return inventoryUserDTO;
	}

	@Override
	public ResponseEntity<Object> findInventoryUserDetailByUserId(Integer userId) {
		try {
			Optional<InventoryUserEntity> i = inventoryUserRepository.findById(userId);
			if (i.isPresent()) {
				InventoryUserDTO inventoryUserDTO = inventoryUserAuthenticationEntityToDTO(i.get());
				return ResponseEntity.status(HttpStatus.OK).body(inventoryUserDTO);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("inventory user authentication detail does not exist. Please enter a correct user id.");
			}

		} catch (Exception e) {
			String message = "failed to get inventory user authentication detail for user id: " + userId;
			log.error(message, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing your request. Please try again later.");
		}

	}

	@Override
	public ResponseEntity<Object> createInventotryUserDetail(InventoryUserDTO inventoryUserDTO) {
		try {
			Optional<InventoryUserEntity> existingUser = inventoryUserRepository
					.findByEmailId(inventoryUserDTO.getEmailId());
			if (existingUser.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with this email already exists.");
			}

			InventoryUserEntity user = new InventoryUserEntity();
			user.setUserName(inventoryUserDTO.getUserName());
			user.setEmailId(inventoryUserDTO.getEmailId());
			user.setPassword(inventoryUserDTO.getPassword());
			user.setRole(inventoryUserDTO.getRole());
			InventoryUserEntity savedUser = inventoryUserRepository.save(user);

			InventoryUserDTO responseDTO = inventoryUserAuthenticationEntityToDTO(savedUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
		} catch (Exception e) {
			log.error("Error creating inventory user: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("something went wrong while creating the user. Please try again later.");
		}
	}

	@Override
	public ResponseEntity<Object> updateInventoryUserDetail(InventoryUserDTO inventoryUserDTO, Integer userId) {
		return inventoryUserRepository.findById(userId).map(record -> {
			record.setUserName(inventoryUserDTO.getUserName());
			record.setEmailId(inventoryUserDTO.getEmailId());
			record.setPassword(inventoryUserDTO.getPassword());
			record.setRole(inventoryUserDTO.getRole());
			return ResponseEntity.ok().body((Object) inventoryUserRepository.save(record));
		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(String.format("inventory user detail by user id - %d does not exists", userId)));
	}

	@Override
	public ResponseEntity<String> deleteInventoryUserDetailByUserId(Integer userId) {
		return inventoryUserRepository.findById(userId).map(record -> {
			inventoryUserRepository.delete(record);
			return ResponseEntity.status(HttpStatus.OK).body("inventory user detail deleted successfully");
		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(String.format("inventory user detail by user id - %d does not exists", userId)));
	}

	public ResponseEntity<Object> loginInventoryUser(String emailId, String password) {
	    try {
	        Optional<InventoryUserEntity> userOpt = inventoryUserRepository.findByEmailId(emailId);
	        if (userOpt.isPresent()) {
	            InventoryUserEntity user = userOpt.get();
	            if (user.getPassword().equals(password)) {
	                InventoryUserDTO userDTO = inventoryUserAuthenticationEntityToDTO(user);
	                return ResponseEntity.ok(userDTO);
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                        .body("Invalid email or password. Please try again.");
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body("Invalid email or password. Please try again.");
	        }
	    } catch (Exception e) {
	        log.error("Failed to login user with email: {}", emailId, e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while processing your request. Please try again later.");
	    }
	}

}
