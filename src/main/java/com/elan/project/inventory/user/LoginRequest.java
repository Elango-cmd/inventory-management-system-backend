package com.elan.project.inventory.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailId;
    private String password;

}
