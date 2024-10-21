package org.example.datafulldisplay.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullPersonNumService;
import org.example.datafulldisplay.service.ImageUploadService;
import org.example.datafulldisplay.service.ws.PersonWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 行人识别接口
 */
@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullPersonNumController {
    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private IFullPersonNumService fullPersonNumService;

    @Autowired
    private PersonWebSocketServer webSocketServer;

    @SneakyThrows
    @PostMapping("/uploadImage")
    public Mono<GlobalResult> handleImageUpload(@RequestParam("file") MultipartFile file) {
        return imageUploadService.uploadImage(file)
                .flatMap(responseValue -> {
                    // 插入数据库
                    fullPersonNumService.insert(responseValue);
                    // 获取总识别人数
                    Integer totalPersonNum = fullPersonNumService.totalPersonNum();
                    // 构建返回数据，包括识别到的人数
                    Map<String, Object> resultData = new HashMap<>();
                    resultData.put("detectedPersonNum", responseValue);
                    resultData.put("totalPersonNum", totalPersonNum);
                    String message = JSONObject.toJSONString(resultData);
                    // 发送信息通过 WebSocket
                    try {
                        webSocketServer.sendInfo(message, "admin");
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                    return Mono.just(GlobalResult.ok(resultData)); // 返回数据字段包含识别人数
                })
                .onErrorResume(e -> Mono.just(GlobalResult.errorMsg(e.getMessage())));
    }
}

