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
    // 允许跨域请求，接受所有来源
    @CrossOrigin(origins = "*")
    // 处理 POST 请求，路径为 /uploadAqi
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

        // 将消息数据转换为 JSON 字符串
        String message = JSON.toJSONString(messageData);

        // 发送信息通过 WebSocket
        try {
            AirQualityWebSocketServer.sendInfo(message, "admin");
        } catch (IOException e) {
            // 如果发生 IO 异常，转换为运行时异常并返回
            return Mono.error(new RuntimeException(e));
        }

        // 返回成功响应
        return Mono.just(GlobalResult.ok(messageData));
    }

    // 获取指定日期的空气质量数据
    @GetMapping("/listByDate")
    public GlobalResult getAirQualityByDate(@RequestParam String date) {
        // 调用服务层方法获取指定日期的空气质量数据，并通过GlobalResult封装结果返回
        return GlobalResult.ok(airQualityService.getAirQualityByDate(date));
    }
    /**
     * 根据两个日期获取空气质量信息
     * 该接口用于接收两个日期参数，并返回这两个日期之间的空气质量数据
     *
     * @param date1 第一个日期，作为查询范围的起始日期
     * @param date2 第二个日期，作为查询范围的结束日期
     * @return 返回包含两个日期之间空气质量信息的GlobalResult对象
     */
    @GetMapping("/listByTwoDates")
    public GlobalResult getAirQualityByTwoDates(@RequestParam String date1, @RequestParam String date2) {
        return GlobalResult.ok(airQualityService.getAirQualityByTwoDates(date1, date2));
    }
}
