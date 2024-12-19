package com.lumiring.updateManager.services.device;

import com.lumiring.updateManager.dao.device.DeviceDaoJdbc;
import com.lumiring.updateManager.dao.user.UserDao;
import com.lumiring.updateManager.domain.api.device.FirmwareTimestampRequest;
import com.lumiring.updateManager.domain.api.user.UserPageResponse;
import com.lumiring.updateManager.domain.constant.Code;
import com.lumiring.updateManager.domain.dto.device.FirmwareTimestamp;
import com.lumiring.updateManager.domain.dto.user.User;
import com.lumiring.updateManager.domain.response.Response;
import com.lumiring.updateManager.domain.response.SuccessResponse;
import com.lumiring.updateManager.domain.response.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final UserDao userDao;
    private final DeviceDaoJdbc deviceDaoJdbc;


    public ResponseEntity<Response> addFirmwareTimestamp(Long userId, FirmwareTimestampRequest request) {
        FirmwareTimestamp timestamp = FirmwareTimestamp.builder()
                .unixtime(request.getUnixtime())
                .deviceModel(request.getDeviceModel())
                .deviceSerial(request.getDeviceSerial())
                .firmwareVersion(request.getFirmwareVersion())
                .build();

        deviceDaoJdbc.addFirmwareTimestamp(userId, timestamp);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Response> getTimestamp(Integer page, Integer size, Long userId, Long beginTime, Long endTime,
                                                 String deviceModel, String deviceSerial) {

        Long totalElements = deviceDaoJdbc.getTotalUserTimestamps(userId);

        List<FirmwareTimestamp> timestampList = deviceDaoJdbc.getFirmwareTimestampList(page, size, userId, beginTime,
                endTime, deviceModel, deviceSerial);

        int totalPages = (int) Math.ceil((double) totalElements / size);


        return new ResponseEntity<>(SuccessResponse.builder()
                .data(UserPageResponse.<FirmwareTimestamp>builder()
                        .content(timestampList)
                        .page(page)
                        .size(size)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .last(page == totalPages)
                        .build()).build(), HttpStatus.OK);

    }

    public ResponseEntity<Response> getTimestampByUsernameOrEmail(Long id, Integer page, Integer size, String username,
                                                                  String email, Long beginTime,
                                                                  Long endTime, String deviceModel,
                                                                  String deviceSerial) {

        if (!StringUtils.hasText(username) && !StringUtils.hasText(email)) {
            throw CommonException.builder()
                    .code(Code.BAD_CREDENTIALS)
                    .userMessage("Username or Email must not be empty")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        User user;
        if (StringUtils.hasText(username)) {
            user = userDao.getUserByUsername(username);
        } else {
            user = userDao.getUserByEmail(email);
        }

        if (user == null){
            throw CommonException.builder().code(Code.NOT_FOUND).userMessage("User not found").httpStatus(HttpStatus.BAD_REQUEST).build();
        }


        List<FirmwareTimestamp> timestampList = deviceDaoJdbc.getFirmwareTimestampList(page, size, user.getId(), beginTime,
                endTime, deviceModel, deviceSerial);

        Long totalElements = deviceDaoJdbc.getTotalUserTimestamps(user.getId());

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(UserPageResponse.<FirmwareTimestamp>builder()
                        .content(timestampList)
                        .page(page)
                        .size(size)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .last(page == totalPages)
                        .build()).build(), HttpStatus.OK);

    }
}
