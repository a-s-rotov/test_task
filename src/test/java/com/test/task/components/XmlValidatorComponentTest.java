package com.test.task.components;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.test.task.exception.ValidateFileException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class XmlValidatorComponentTest {

    @InjectMocks
    XmlValidatorComponent xmlValidatorComponent;

    @Test
    void checkValidFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/xml/epaperRequest.xml");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name",
                "originalFileName", "text/plain", classPathResource.getInputStream());

        xmlValidatorComponent.valid(mockMultipartFile);
    }

    @Test
    void checkShortValidFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/xml/epaperRequest_short.xml");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name",
                "originalFileName", "text/plain", classPathResource.getInputStream());

        xmlValidatorComponent.valid(mockMultipartFile);
    }

    @Test
    void checkInvalidFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(
                "/xml/epaperRequest_invalid.xml");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name",
                "originalFileName", "text/plain", classPathResource.getInputStream());
        assertThrows(ValidateFileException.class, () -> {
            xmlValidatorComponent.valid(mockMultipartFile);
        });
    }
}
