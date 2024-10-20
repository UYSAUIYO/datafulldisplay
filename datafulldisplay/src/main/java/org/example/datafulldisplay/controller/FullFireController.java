package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.DTO.FullCoordinateDTO;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.ImageUploadService;
import org.example.datafulldisplay.service.ws.FireWebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fire")
public class FullFireController {
    private final ImageUploadService imageUploadService;
    private final FireWebSocketServer webSocketServer;

    public FullFireController(ImageUploadService imageUploadService, FireWebSocketServer webSocketServer) {
        this.imageUploadService = imageUploadService;
        this.webSocketServer = webSocketServer;
    }

    @PostMapping("/uploadImage")
    public GlobalResult handleImageUpload(@RequestParam("file") MultipartFile file,
                                          @RequestParam("x") int x,
                                          @RequestParam("y") int y) throws Exception {
        FullCoordinateDTO coordinateDTO = new FullCoordinateDTO();
        coordinateDTO.setX(x);
        coordinateDTO.setY(y);

        try {
            // 上传图片并获取检测到的火灾数量
            Mono<Long> response = imageUploadService.uploadImage1(file);
            response.map(responseValue -> {
                if (responseValue >= 1) {
                    coordinateDTO.setType("fire");
                }
                return GlobalResult.ok();
            });

            // 通过 WebSocket 发送坐标和火情信息
            webSocketServer.sendInfo(coordinateDTO.toString(), "admin");

            return GlobalResult.ok(coordinateDTO);
        } catch (Exception e) {
            return GlobalResult.errorMsg(e.getMessage());
        }
    }
}
