package com.lumiring.updateManager.config;

import com.lumiring.updateManager.dao.user.UserDao;
import com.lumiring.updateManager.domain.dto.user.Role;
import com.lumiring.updateManager.domain.dto.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AdminInitializer implements CommandLineRunner {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;
    private final String email;

    public AdminInitializer(
            UserDao userDao,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username}") String username,
            @Value("${app.admin.password}") String password,
            @Value("${app.admin.email}") String email
    ) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (!userDao.isExistsUsername(username) && !userDao.isExistsEmail(email)) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ROLE_ADMIN);

            var user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .roles(roles)
                    .build();

            userDao.insertNewUser(user);
            log.info("Admin with username: {} created", username);
            log.info("email {} created", email);
        } else {
            log.info("Admin with username: {} or with email: {} is exist", username, email);
        }
    }
}
