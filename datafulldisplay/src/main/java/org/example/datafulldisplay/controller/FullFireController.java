package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.DTO.FullCoordinateDTO;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.ImageUploadService;
import org.example.datafulldisplay.service.ws.FireWebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/fire")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullFireController {
    private final ImageUploadService imageUploadService;
    private final FireWebSocketServer webSocketServer;

    public FullFireController(ImageUploadService imageUploadService, FireWebSocketServer webSocketServer) {
        this.imageUploadService = imageUploadService;
        this.webSocketServer = webSocketServer;
    }

    /**
     * 处理图像上传请求，并根据图像内容和坐标信息进行处理
     *
     * @param file 图像文件，通过表单参数 "file" 上传
     * @param x    图像的横坐标，表示图像在某个平面上的位置
     * @param y    图像的纵坐标，表示图像在某个平面上的位置
     * @return 返回一个 Mono<GlobalResult> 对象，包含处理结果和可能的坐标信息
     */
    @PostMapping("/uploadImage")
    public Mono<GlobalResult> handleImageUpload(@RequestParam("file") MultipartFile file,
                                                @RequestParam("x") int x,
                                                @RequestParam("y") int y) {
        // 创建一个 FullCoordinateDTO 对象，用于存储坐标信息
        FullCoordinateDTO coordinateDTO = new FullCoordinateDTO();
        coordinateDTO.setX(x);
        coordinateDTO.setY(y);
        // 调用服务方法上传图像，并根据响应值确定是否存在火情
        return imageUploadService.uploadImage1(file,coordinateDTO.getX(),coordinateDTO.getY())
                .flatMap(responseValue -> {
                    // 根据响应值设置火情类型
                    if (responseValue != null && responseValue >= 1) {
                        coordinateDTO.setType("fire");
                    }
                    // 通过 WebSocket 发送坐标和火情信息
                    try {
                        webSocketServer.sendInfo(coordinateDTO.toString(), "admin");

                    } catch (IOException e) {
                        // 如果发送信息时发生异常，返回错误的 Mono 对象
                        return Mono.error(e);
                    }
                    // 返回包含处理结果和坐标信息的成功响应
                    return Mono.just(GlobalResult.ok(coordinateDTO));
                })
                // 如果在处理过程中遇到错误，返回包含错误信息的响应
                .onErrorResume(e -> Mono.just(GlobalResult.errorMsg(e.getMessage())));
    }

}
