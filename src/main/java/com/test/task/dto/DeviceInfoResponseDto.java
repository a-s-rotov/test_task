package com.test.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfoResponseDto {

    private String newspaperName;
    private Integer width;
    private Integer height;
    private Integer dpi;

}
