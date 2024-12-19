package com.lumiring.updateManager.dao.device;


import com.lumiring.updateManager.domain.dto.device.FirmwareTimestamp;
import com.lumiring.updateManager.domain.dto.device.FirmwareTimestampMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional
public class DeviceDaoJdbc extends JdbcDaoSupport {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    public List<FirmwareTimestamp> getFirmwareTimestampList(
            Integer page,
            Integer size,
            Long userId,
            Long beginTime,
            Long endTime,
            String deviceModel,
            String deviceSerial) {

        StringBuilder sql = new StringBuilder("SELECT * FROM device.firmware_timestamp WHERE user_id = :user_id");
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);


        if (beginTime != null) {
            sql.append(" AND unixtime >= :beginTime");
            params.put("beginTime", beginTime);
        }
        if (endTime != null) {
            sql.append(" AND unixtime <= :endTime");
            params.put("endTime", endTime);
        }
        if (deviceModel != null && !deviceModel.isEmpty()) {
            sql.append(" AND device_model = :deviceModel");
            params.put("deviceModel", deviceModel);
        }
        if (deviceSerial != null && !deviceSerial.isEmpty()) {
            sql.append(" AND device_serial = :deviceSerial");
            params.put("deviceSerial", deviceSerial);
        }


        int offset = (page != null && size != null) ? (page - 1) * size : 0;
        sql.append(" ORDER BY unixtime DESC");
        sql.append(" LIMIT :limit OFFSET :offset");
        params.put("limit", size != null ? size : Integer.MAX_VALUE);
        params.put("offset", offset);


        return namedParameterJdbcTemplate.query(
                sql.toString(),
                new MapSqlParameterSource(params),
                new FirmwareTimestampMapper()
        );
    }


    public void addFirmwareTimestamp(Long userId, FirmwareTimestamp firmwareTimestamp) {
        String sql = "INSERT INTO device.firmware_timestamp (user_id, unixtime, device_model, device_serial, firmware_version) " +
                "VALUES(:user_id, :unixtime, :device_model, :device_serial, :firmware_version);";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("unixtime", firmwareTimestamp.getUnixtime());
        params.put("device_model", firmwareTimestamp.getDeviceModel());
        params.put("device_serial", firmwareTimestamp.getDeviceSerial());
        params.put("firmware_version", firmwareTimestamp.getFirmwareVersion());

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
    }

    public Long getTotalUserTimestamps(Long userId) {
        String sql = "SELECT COUNT(*) FROM device.firmware_timestamp WHERE user_id = :userId;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }


}
