package com.lumiring.updateManager.domain.api.user.auth;


import com.lumiring.updateManager.domain.constant.RegExp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {

    @Size(min = 4, max = 50, message = "Username must contain from 4 to 50 characters")
    @NotBlank(message = "username must not be empty")
    @Pattern(regexp = RegExp.username, message ="Username contains prohibited characters")
    private String username;

    @Size(min = 8, max = 100, message = "Password must contain from 8 to 100 characters")
    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp = RegExp.password, message = "Password contains prohibited characters")
    private String password;

    @Size(max = 100, message = "E-mail must contain 100 characters maximum")
//    @NotBlank(message = "E-mail must not be empty")
    @Pattern(regexp = RegExp.email, message = "Incorrect E-mail")
    private String email;

    @Size(max = 255, message = "Company must contain 100 characters maximum")
    private String company;


}
