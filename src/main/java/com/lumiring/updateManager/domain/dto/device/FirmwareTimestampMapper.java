package com.lumiring.updateManager.domain.dto.device;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirmwareTimestampMapper implements RowMapper<FirmwareTimestamp> {
    @Override
    public @NotNull FirmwareTimestamp mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FirmwareTimestamp.builder()
                .unixtime(rs.getLong("unixtime"))
                .deviceModel(rs.getString("device_model"))
                .deviceSerial(rs.getString("device_serial"))
                .firmwareVersion(rs.getString("firmware_version"))
                .build();
    }
}
