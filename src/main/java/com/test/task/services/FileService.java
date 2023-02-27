package com.test.task.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.task.dto.DeviceInfoResponseDto;
import com.test.task.exception.ParsingFileException;
import com.test.task.model.db.DeviceInfoEntity;
import com.test.task.model.xml.EPaperRequest;
import com.test.task.repositories.DeviceInfoRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class FileService {

    private final DeviceInfoRepository deviceInfoRepository;
    private final XmlMapper xmlMapper;

    @Transactional
    public void saveDeviceInfo(MultipartFile file) {
        EPaperRequest ePaperRequest = parseFile(file);
        deviceInfoRepository.save(new DeviceInfoEntity(
                ePaperRequest.getDeviceInfo().getAppInfo().getNewspaperName(),
                ePaperRequest.getDeviceInfo().getScreenInfo().getWidth(),
                ePaperRequest.getDeviceInfo().getScreenInfo().getHeight(),
                ePaperRequest.getDeviceInfo().getScreenInfo().getDpi()));
    }

    @Transactional(readOnly = true)
    public List<DeviceInfoResponseDto> findDeviceInfo(String newspaperName, Integer width,
            Integer height, Integer dpi,
            Integer page, Integer size, String sortingField) {

        Page<DeviceInfoEntity> deviceList = deviceInfoRepository.findByParams(
                newspaperName, width, height, dpi,
                PageRequest.of(page, size, Sort.by(sortingField)));

        return deviceList.getContent().stream().map(item -> DeviceInfoResponseDto.builder()
                        .dpi(item.getDpi())
                        .width(item.getWidth())
                        .height(item.getHeight())
                        .newspaperName(item.getNewspaperName())
                        .build())
                .collect(Collectors.toList());
    }


    private EPaperRequest parseFile(MultipartFile file) {

        try {
            return xmlMapper.readValue(file.getInputStream(), EPaperRequest.class);
        } catch (IOException e) {
            throw new ParsingFileException(e);
        }
    }

}
