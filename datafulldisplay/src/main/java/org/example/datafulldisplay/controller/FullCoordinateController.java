package org.example.datafulldisplay.controller;

import com.alibaba.fastjson.JSON;
import org.example.datafulldisplay.domain.FullCoordinate;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullCoordinateService;
import org.example.datafulldisplay.service.ws.CoordinateWebSocketServer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/coordinate")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullCoordinateController {
    private final IFullCoordinateService fullCoordinateService;

    public FullCoordinateController(IFullCoordinateService fullCoordinateService) {
        this.fullCoordinateService = fullCoordinateService;
    }

    /**
     * 获取坐标列表
     *
     * @param fullCoordinate 包含查询条件的坐标信息对象，用于指定查询的详细参数
     * @return 返回一个GlobalResult对象，其中包含根据查询条件获取的坐标列表信息
     */
    @GetMapping("/list")
    public GlobalResult list(FullCoordinate fullCoordinate) {
        return fullCoordinateService.selectFullCoordinateList(fullCoordinate);
    }

    @PostMapping
    public GlobalResult add(@RequestBody FullCoordinate fullCoordinate) throws IOException {
        // 保存坐标信息到数据库
        GlobalResult result = fullCoordinateService.addFullCoordinate(fullCoordinate);

        // 将坐标信息转换为 JSON 字符串
        String message = JSON.toJSONString(fullCoordinate);
        System.out.println(message);

        // 通过 WebSocket 发送给前端
        CoordinateWebSocketServer.sendInfo(message);

        return result;
    }
}
