package com.elan.project.inventory.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryUserRepository extends JpaRepository<InventoryUserEntity, Integer>{

	Optional<InventoryUserEntity> findByEmailId(String emailId);

	@Query("SELECT u FROM InventoryUserEntity u WHERE u.emailId = :emailId AND u.password = :password")
	Optional<InventoryUserEntity> findByEmailIdAndPassword(@Param("emailId") String emailId, @Param("password") String password);

}
