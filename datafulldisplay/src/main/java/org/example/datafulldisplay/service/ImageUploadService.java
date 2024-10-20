package org.example.datafulldisplay.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Service
public class ImageUploadService {

    private final WebClient webClient = WebClient.builder().build();

    /***
     * 使用 webclient 将图片转发到 Python API 进行行人识别
     * @param file 上传的图片文件
     * @return 检测到的 "person" 数量
     * @throws Exception
     */
    public Mono<Long> uploadImage(MultipartFile file) throws Exception {
        String pythonApiUrl = "http://192.168.10.9:8510/predict/person"; // 行人识别 Python API 地址，请自行修改

        return webClient.post()
                .uri(pythonApiUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file.getResource()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }) // 解析 JSON 响应
                .map(response -> {
                    Map<String, Object> labelCounts = (Map<String, Object>) response.get("label_counts");
                    // 获取 "person" 标签的数量
                    return labelCounts != null && labelCounts.containsKey("person")
                            ? ((Number) labelCounts.get("person")).longValue()
                            : 0L;
                });
    }

    /***
     * 使用 WebClient 上传图片到 Python API 进行火情识别
     * @param file 上传的图片文件
     * @return 检测到的 "fire" 数量
     * @throws Exception
     */
    public Mono<Long> uploadImage1(MultipartFile file) throws Exception {
        String pythonApiUrl = "http://192.168.10.9:8510/predict/fire"; // 火情识别 Python API 地址

        return webClient.post()
                .uri(pythonApiUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file.getResource())) // 上传图片
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}) // 解析 JSON 响应
                .map(response -> {
                    Map<String, Object> labelCounts = (Map<String, Object>) response.get("label_counts");
                    // 获取 "fire" 标签的数量
                    return labelCounts != null && labelCounts.containsKey("fire")
                            ? ((Number) labelCounts.get("fire")).longValue()
                            : 0L;
                });
    }
}