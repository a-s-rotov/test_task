package com.test.task.controllers;

import com.test.task.components.XmlValidatorComponent;
import com.test.task.dto.DeviceInfoResponseDto;
import com.test.task.dto.FileUploadResponseDto;
import com.test.task.services.FileService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class FileController {

    private XmlValidatorComponent xmlValidatorComponent;
    private FileService fileService;

    @PostMapping("/deviceInfo")
    public FileUploadResponseDto uploadFile(
            @RequestParam("file") MultipartFile file) {

        xmlValidatorComponent.valid(file);

        fileService.saveDeviceInfo(file);

        return FileUploadResponseDto.builder()
                .message("File " + file.getOriginalFilename() + " is uploaded")
                .build();
    }

    @GetMapping("/deviceInfo")
    public List<DeviceInfoResponseDto> findDeviceInfo(@RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam() String sortingField,
            @RequestParam(required = false) String newspaperName,
            @RequestParam(required = false) Integer width,
            @RequestParam(required = false) Integer height,
            @RequestParam(required = false) Integer dpi) {

        return fileService.findDeviceInfo(newspaperName, width, height, dpi, page, size,
                sortingField);
    }
}
