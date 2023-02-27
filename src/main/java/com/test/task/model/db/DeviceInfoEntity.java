package com.test.task.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class DeviceInfoEntity {


    public DeviceInfoEntity() { }

    public DeviceInfoEntity(String newspaperName, Integer width, Integer height, Integer dpi) {
        this.newspaperName = newspaperName;
        this.width = width;
        this.height = height;
        this.dpi = dpi;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String newspaperName;
    private Integer width;
    private Integer height;
    private Integer dpi;

}
