package com.test.task.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import com.test.task.dto.DeviceInfoResponseDto;
import com.test.task.model.db.DeviceInfoEntity;
import com.test.task.repositories.DeviceInfoRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    FileService fileService;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;


    @BeforeEach
    void init(){
        deviceInfoRepository.deleteAll();
        deviceInfoRepository.save(new DeviceInfoEntity("1",1,1,1));
        deviceInfoRepository.save(new DeviceInfoEntity("2",2,2,2));
        deviceInfoRepository.save(new DeviceInfoEntity("3",3,3,3));
    }

    @Test
    void findAllTest(){
        List<DeviceInfoResponseDto> devices = fileService.findDeviceInfo(null, null, null,
                null, 0, 10, "newspaperName");

        assertThat(devices).hasSize(3);
    }

    @Test
    void findByName(){
        List<DeviceInfoResponseDto> devices = fileService.findDeviceInfo("2", null, null,
                null, 0, 10, "newspaperName");

        assertThat(devices).hasSize(1)
                .extracting(DeviceInfoResponseDto::getNewspaperName)
                .containsExactlyInAnyOrder("2");
    }

    @Test
    void findNothing(){
        List<DeviceInfoResponseDto> devices = fileService.findDeviceInfo("4", null, null,
                null, 0, 10, "newspaperName");

        assertThat(devices).isNotNull().isEmpty();
    }


}
