package com.lumiring.updateManager.domain.api.device;

import com.lumiring.updateManager.domain.dto.device.FirmwareTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirmwareTimestampListResponse {
    List<FirmwareTimestamp> timestampList;
}
