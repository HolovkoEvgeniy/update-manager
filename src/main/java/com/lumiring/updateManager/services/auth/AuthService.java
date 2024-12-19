package com.lumiring.updateManager.services.auth;

import com.lumiring.updateManager.dao.user.UserDao;
import com.lumiring.updateManager.domain.dto.user.Role;
import com.lumiring.updateManager.domain.dto.user.User;
import com.lumiring.updateManager.domain.api.user.auth.SingInRequest;
import com.lumiring.updateManager.domain.api.user.auth.SingUpRequest;
import com.lumiring.updateManager.domain.api.user.auth.SingInResponse;
import com.lumiring.updateManager.domain.api.user.auth.SingUpResponse;
import com.lumiring.updateManager.domain.response.Response;
import com.lumiring.updateManager.domain.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Response> signUp(@RequestBody final SingUpRequest request) {

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .company(request.getCompany())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        userDao.insertNewUser(user);

        var jwt = jwtProvider.generateAccessToken(user);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SingUpResponse.builder().accessToken(jwt).build()).build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> signIn(@RequestBody final SingInRequest request) {


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

        var user = userDao.getUserByUsername(request.getLogin());
        var jwt = jwtProvider.generateAccessToken(user);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SingInResponse.builder().accessToken(jwt).build()).build(), HttpStatus.OK);
    }


}
