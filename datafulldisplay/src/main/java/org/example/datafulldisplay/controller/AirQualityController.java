package org.example.datafulldisplay.controller;

import com.alibaba.fastjson.JSON;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IAirQualityService;
import org.example.datafulldisplay.service.ws.AirQualityWebSocketServer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/airQuality")
@CrossOrigin(origins = "*")
public class AirQualityController {

    private final IAirQualityService airQualityService;

    public AirQualityController(IAirQualityService airQualityService) {
        this.airQualityService = airQualityService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/uploadAqi")
    public Mono<GlobalResult> uploadAqi(@RequestParam("aqi") Integer aqi) {
        // 插入数据库
        airQualityService.insertAirQuality(aqi);

        // 准备 WebSocket 推送信息
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("aqi", aqi);

        // 映射 AQI 到百分比
        double percentage = (500 - aqi) / 500.0 * 99 + 1;
        percentage = Math.max(1, Math.min(percentage, 100));
        messageData.put("percentage", percentage);

        messageData.put("uploadTime", LocalDate.now().toString());
        messageData.put("type", "airQualityUpdate");

        String message = JSON.toJSONString(messageData);

        // 发送信息通过 WebSocket
        try {
            AirQualityWebSocketServer.sendInfo(message, "admin");
        } catch (IOException e) {
            return Mono.error(new RuntimeException(e));
        }

        return Mono.just(GlobalResult.ok(messageData));
    }

    // 获取指定日期的空气质量数据
    @GetMapping("/listByDate")
    public GlobalResult getAirQualityByDate(@RequestParam String date) {
        return GlobalResult.ok(airQualityService.getAirQualityByDate(date));
    }
    @GetMapping("/listByTwoDates")
    public GlobalResult getAirQualityByTwoDates(@RequestParam String date1, @RequestParam String date2) {
        return GlobalResult.ok(airQualityService.getAirQualityByTwoDates(date1, date2));
    }
}
