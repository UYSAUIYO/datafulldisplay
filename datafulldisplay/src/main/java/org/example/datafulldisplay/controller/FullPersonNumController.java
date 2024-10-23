package org.example.datafulldisplay.controller;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.example.datafulldisplay.domain.FullPersonNum;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullPersonNumService;
import org.example.datafulldisplay.service.ImageUploadService;
import org.example.datafulldisplay.service.ws.PersonWebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行人识别接口
 */
@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullPersonNumController {
    private final ImageUploadService imageUploadService;

    private final IFullPersonNumService fullPersonNumService;

    public FullPersonNumController(ImageUploadService imageUploadService, IFullPersonNumService fullPersonNumService) {
        this.imageUploadService = imageUploadService;
        this.fullPersonNumService = fullPersonNumService;
    }

    // 使用 SneakyThrows 注解来处理可能抛出的受检异常
    @SneakyThrows
    // 处理 POST 请求中的 /uploadImage 路径，用于上传图片
    @PostMapping("/uploadImage")
    public Mono<GlobalResult> handleImageUpload(@RequestParam("file") MultipartFile file) {
        // 调用 imageUploadService 的 uploadImage 方法上传图片，并进行后续处理
        return imageUploadService.uploadImage(file)
                .flatMap(responseValue -> {
                    // 插入数据库
                    fullPersonNumService.insert(responseValue);
                    // 获取总识别人数
                    Integer totalPersonNum = fullPersonNumService.totalPersonNum();
                    // 准备 WebSocket 推送信息
                    Map<String, Object> messageData = new HashMap<>();
                    messageData.put("type", "update");
                    messageData.put("detectedPersonNum", responseValue);
                    messageData.put("totalPersonNum", totalPersonNum);
                    messageData.put("uploadTime", LocalDateTime.now().toString());
                    String message = JSON.toJSONString(messageData);
                    System.out.println(message);

                    // 发送信息通过 WebSocket
                    try {
                        PersonWebSocketServer.sendInfo(message, "admin");
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                    // 构建返回数据，包括识别到的人数
                    Map<String, Object> resultData = new HashMap<>();
                    resultData.put("detectedPersonNum", responseValue);
                    resultData.put("totalPersonNum", totalPersonNum);

                    return Mono.just(GlobalResult.ok(resultData));
                })
                // 错误处理：返回错误信息
                .onErrorResume(e -> Mono.just(GlobalResult.errorMsg(e.getMessage())));
    }
    /**
     * 获取识别人数的列表
     */
    @GetMapping("/list")
    public GlobalResult getPersonNumList() {
        return fullPersonNumService.getPersonNumList();
    }

    /**
     * 获取最新的识别人数
     */
    @GetMapping("/latest")
    public GlobalResult getLatestPersonNum() {
        return fullPersonNumService.getLatestPersonNum();
    }

    // 获取指定日期的人流数据
    @GetMapping("/listByDate")
    public GlobalResult getPersonNumListByDate(@RequestParam String date) {
        List<FullPersonNum> personNumList = fullPersonNumService.getPersonNumListByDate(date);
        return GlobalResult.ok(personNumList);
    }
}

