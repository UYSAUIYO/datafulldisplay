package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullPersonNumService;
import org.example.datafulldisplay.service.ImageUploadService;
import org.example.datafulldisplay.service.ws.PersonWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 行人识别接口
 */
@RestController
@RequestMapping("/person")
public class FullPersonNumController {
    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private IFullPersonNumService fullPersonNumService;

    @Autowired
    private PersonWebSocketServer webSocketServer;

    @PostMapping("/uploadImage")
    public GlobalResult handleImageUpload(@RequestParam("file") MultipartFile file) {
        try {
            Mono<Long> response = imageUploadService.uploadImage(file);
            response.map(responseValue -> {
                fullPersonNumService.insert(responseValue);
                Integer totalPersonNum = fullPersonNumService.totalPersonNum();
                String message = "当前识别人数：" + responseValue + "，总识别人数：" + totalPersonNum;
                try {
                    webSocketServer.sendInfo(message, "admin");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return GlobalResult.ok();
            });
            return GlobalResult.ok(response);
        } catch (Exception e) {
            return GlobalResult.errorMsg(e.getMessage());
        }
    }
}
