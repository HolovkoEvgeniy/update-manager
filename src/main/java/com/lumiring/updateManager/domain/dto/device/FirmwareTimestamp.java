package com.lumiring.updateManager.domain.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirmwareTimestamp {

    private Long unixtime;
    private String deviceModel;
    private String deviceSerial;
    private String firmwareVersion;
}
