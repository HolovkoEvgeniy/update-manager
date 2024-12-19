package com.lumiring.updateManager.controllers;

import com.lumiring.updateManager.domain.api.device.FirmwareTimestampRequest;
import com.lumiring.updateManager.domain.response.Response;
import com.lumiring.updateManager.security.UserDetailsImpl;
import com.lumiring.updateManager.services.device.DeviceService;
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
@RequestMapping("api/v1/device")
public class UpdateManagerController {
    private final DeviceService deviceService;



    @GetMapping("timestamp")
    public ResponseEntity<Response> getTimestamp(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "50") Integer size,
                                                 @RequestParam(required = false) Long beginTime,
                                                 @RequestParam(required = false) Long endTime,
                                                 @RequestParam(required = false) String deviceModel,
                                                 @RequestParam(required = false) String deviceSerial) {

        int maxSize = 100;
        size = Math.min(size, maxSize);
        return deviceService.getTimestamp(page, size, userDetails.user().getId(), beginTime, endTime, deviceModel, deviceSerial);
    }

    @GetMapping("admin/timestamp")
    public ResponseEntity<Response> getTimestampByUsernameOrEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @RequestParam(defaultValue = "1") Integer page,
                                                                  @RequestParam(defaultValue = "50") Integer size,
                                                                  @RequestParam(required = false) String username,
                                                                  @RequestParam(required = false) String email,
                                                                  @RequestParam(required = false) Long beginTime,
                                                                  @RequestParam(required = false) Long endTime,
                                                                  @RequestParam(required = false) String deviceModel,
                                                                  @RequestParam(required = false) String deviceSerial) {


        int maxSize = 100;
        size = Math.min(size, maxSize);
        return deviceService.getTimestampByUsernameOrEmail(userDetails.user().getId(), page, size, username, email, beginTime, endTime, deviceModel, deviceSerial);
    }


    @PostMapping("timestamp")
    public ResponseEntity<Response> addTimestamp(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @Valid @RequestBody final FirmwareTimestampRequest request) {

        return deviceService.addFirmwareTimestamp(userDetails.user().getId(), request);
    }


}
