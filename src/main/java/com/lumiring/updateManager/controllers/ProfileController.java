package com.lumiring.updateManager.controllers;


import com.lumiring.updateManager.domain.api.user.UpdateUserCredsRequest;
import com.lumiring.updateManager.domain.api.user.UpdateUserInfoRequest;
import com.lumiring.updateManager.domain.api.user.common.DeleteByIdRequest;
import com.lumiring.updateManager.domain.response.Response;
import com.lumiring.updateManager.security.UserDetailsImpl;
import com.lumiring.updateManager.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/profile")
public class ProfileController {
    private final UserService userService;

    @GetMapping("/userData")
    public ResponseEntity<Response> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.user().getId());
    }

    @PostMapping("/updateUserCreds")
    public ResponseEntity<Response> updateUserCreds(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @Valid @RequestBody final UpdateUserCredsRequest credsRequest) {
        return userService.updateUserCreds(userDetails.user().getId(), credsRequest);
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<Response> updateUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @Valid @RequestBody final UpdateUserInfoRequest infoRequest) {
        return userService.updateUserInfo(userDetails.user().getId(), infoRequest);
    }

    @GetMapping("/admin/userProfile")
    public ResponseEntity<Response> getUserProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email) {

        return userService.getProfileByUsernameOrEmail(username, email);
    }


    @GetMapping("admin/userProfileList")
    public ResponseEntity<Response> getUserProfileList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int size) {

        int maxSize = 100;
        size = Math.min(size, maxSize);
        return userService.getUserProfileList(page, size);
    }

    @DeleteMapping("admin/deleteUser")
    public ResponseEntity<Response> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @Valid @RequestBody final DeleteByIdRequest deleteRequest) {
        return userService.deleteUser(deleteRequest.getId());
    }

}
