package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.DTO.FullCoordinateDTO;
import org.example.datafulldisplay.domain.VO.FullCoordinateVO;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.ImageUploadService;
import org.example.datafulldisplay.service.ws.FireWebSocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/uploadImageUrl")
    public GlobalResult handleImageUpload(@RequestBody FullCoordinateVO fullCoordinateVO) throws Exception {
        String imageUrl = fullCoordinateVO.getImageUrl();
        FullCoordinateDTO coordinateDTO = new FullCoordinateDTO();
        coordinateDTO.setX(fullCoordinateVO.getX());
        coordinateDTO.setY(fullCoordinateVO.getY());
        try {
            Mono<Long> response = imageUploadService.uploadImageUrl(imageUrl);
            response.map(responseValue -> {
                if (responseValue >= 1)
                    coordinateDTO.setType("fire");
                return GlobalResult.ok();
            });
            webSocketServer.sendInfo(coordinateDTO.toString(), "admin");
            return GlobalResult.ok();
        } catch (Exception e) {
            return GlobalResult.errorMsg(e.getMessage());
        }
    }
}
