package org.example.datafulldisplay.service;

import com.alibaba.fastjson.JSONObject;
import org.example.datafulldisplay.controller.FullFireController;
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

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    /***
     * 使用 webclient 将图片转发到 Python API 进行行人识别
     * @param file 上传的图片文件
     * @return 检测到的 "person" 数量
     */
    public Mono<Long> uploadImage(MultipartFile file) {
        String pythonApiUrl = "http://localhost:8510/predict/person"; // 行人识别 Python API 地址，请自行修改

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
     */
    public Mono<Long> uploadImage1(MultipartFile file,int x,int y) {
        String pythonApiUrl = "http://localhost:8510/predict/fire"; // 火情识别 Python API 地址

        return webClient.post()
                .uri(pythonApiUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file.getResource())) // 上传图片
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .doOnNext(response -> {
                    // 输出收到的响应，便于调试
                    System.out.println("Received response: " + response);
                })
                .map(response -> {
                    Map<String, Object> labelCounts = (Map<String, Object>) response.get("label_counts");
                    // 获取 "fire" 标签的数量
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("x", x);
                    jsonObject.put("y", y);
                    jsonObject.put("coordinateLocal", "A");
                    jsonObject.put("coordinateType", "warning");
                    System.out.println("json信息"+jsonObject);
                    sendPostRequest(jsonObject.toString());
                    return labelCounts != null && labelCounts.containsKey("fire")
                            ? ((Number) labelCounts.get("fire")).longValue()
                            : 0L;
                });
    }

    public void sendPostRequest(String value) {
        System.out.println(value);
        // 发送 POST 请求
        Mono<String> response = webClient.post()
                .uri("/coordinate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(value)
                .retrieve()
                .bodyToMono(String.class);

        // 处理响应
        response.subscribe(
                result -> System.out.println("响应结果: " + result),
                error -> System.err.println("请求失败: " + error.getMessage())
        );
    }
}