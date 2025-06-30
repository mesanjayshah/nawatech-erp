package io.nawatech.erp.utils;

import lombok.Data;

@Data
public class PasswordRequestUtil {
    private String email;
    private String password;
    private String newPassword;
    private String oldPassword;
}
