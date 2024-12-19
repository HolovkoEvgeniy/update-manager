package com.lumiring.updateManager.domain.dto.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_ROOT("ROLE_ROOT"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }
}
