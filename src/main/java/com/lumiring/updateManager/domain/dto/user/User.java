package com.lumiring.updateManager.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonView(User.WithoutPasswordView.class)
//@Entity(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Long id;
    private String username;
    private String email;
    private String company;

//    @JsonView(WithPasswordView.class)

    private String password;

    private Set<Role> roles;
}
