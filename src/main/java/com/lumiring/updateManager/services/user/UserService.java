package com.lumiring.updateManager.services.user;
import com.lumiring.updateManager.dao.user.UserDao;
import com.lumiring.updateManager.domain.api.user.*;
import com.lumiring.updateManager.domain.constant.Code;
import com.lumiring.updateManager.domain.dto.user.User;
import com.lumiring.updateManager.domain.response.Response;
import com.lumiring.updateManager.domain.response.SuccessResponse;
import com.lumiring.updateManager.domain.response.exception.CommonException;
import com.lumiring.updateManager.services.auth.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public ResponseEntity<Response> updateUserCreds(Long userId, UpdateUserCredsRequest userRequest) {
        User user = userDao.getUserById(userId);

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw CommonException.builder().code(Code.AUTHORIZATION_ERROR).userMessage("Wrong password").httpStatus(HttpStatus.BAD_REQUEST).build();
        }

        if (userRequest.getUsername() != null) {
            user.setUsername(userRequest.getUsername());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getNewPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequest.getNewPassword()));
        }

        User returnedUser = userDao.updateUserCreds(user);

        String jwt = jwtProvider.generateAccessToken(returnedUser);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(
                                UpdateUserCredsResponse.builder()
                                        .email(returnedUser.getEmail())
                                        .username(returnedUser.getUsername())
                                        .accessToken(jwt)
                                        .build()
                        )
                        .build(),
                HttpStatus.OK
        );
    }


    public ResponseEntity<Response> updateUserInfo(Long userId, UpdateUserInfoRequest infoRequest) {
        User user = userDao.getUserById(userId);

        if (infoRequest.getCompany() != null) {
            user.setCompany(infoRequest.getCompany());
        }

        User returnedUser = userDao.updateUserCreds(user);

        return new ResponseEntity<>(
                SuccessResponse.builder()
                        .data(
                                UserResponse.builder()
                                        .company(returnedUser.getCompany())
                                        .build()
                        )
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Response> getProfile(Long userId) {
        User user = userDao.getUserById(userId);
        return new ResponseEntity<>(SuccessResponse.builder().data(UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .company(user.getCompany())
                .build()).build(), HttpStatus.OK);
    }


    public ResponseEntity<Response> getProfileByUsernameOrEmail(String username, String email) {
        User user = new User();
        if (StringUtils.hasText(username)) {
            user = userDao.getUserByUsername(username);
        } else if (StringUtils.hasText(email)) {
            user = userDao.getUserByEmail(email);
        }
        return new ResponseEntity<>(SuccessResponse.builder().data(UserResponse.builder()
                .email(user.getEmail()).username(user.getUsername()).build()).build(), HttpStatus.OK);
    }


    public ResponseEntity<Response> getUserProfileList(Integer page, Integer size) {

        Long totalElements = userDao.getTotalUsers();
        List<User> userList = userDao.getUserProfileList(page, size);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(UserPageResponse.<User>builder()
                        .content(userList)
                        .page(page)
                        .size(size)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .last(page == totalPages)
                        .build()).build(), HttpStatus.OK);
    }


    public ResponseEntity<Response> deleteUser(Long userId) {
        userDao.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
