package com.test.task;

import static org.assertj.core.api.Assertions.assertThat;

import com.test.task.dto.DeviceInfoResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskApplicationTestsIT {

    @Value(value = "${local.server.port}")
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void uploadAndReadData() {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource("/xml/epaperRequest.xml"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> uploadResult = restTemplate.exchange(
                "http://localhost:" + port + "/deviceInfo", HttpMethod.POST, requestEntity,
                String.class);

        assertThat(uploadResult.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        ResponseEntity<List<DeviceInfoResponseDto>> findResult = restTemplate.exchange(
                "http://localhost:" + port
                        + "/deviceInfo?page=0&size=10&sortingField=newspaperName&newspaperName=abb&width=1280&height=752&dpi=160",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DeviceInfoResponseDto>>() {
                });

        assertThat(findResult.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(findResult.getBody()).hasSize(1)
                .contains(new DeviceInfoResponseDto("abb", 1280, 752, 160));
    }

    @Test
    void uploadInvalidFile() {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource("/xml/epaperRequest_invalid.xml"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> uploadResult = restTemplate.exchange(
                "http://localhost:" + port + "/deviceInfo", HttpMethod.POST, requestEntity,
                String.class);

        assertThat(uploadResult.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

}
