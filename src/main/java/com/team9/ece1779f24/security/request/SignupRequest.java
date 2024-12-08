package com.team9.ece1779f24.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;


@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20, message = "user must >3 <20")
    private String username;

    @NotBlank
    @Size(max = 50, message = "mail must <50")
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40, message = "password must >6 <40")
    private String password;
}
