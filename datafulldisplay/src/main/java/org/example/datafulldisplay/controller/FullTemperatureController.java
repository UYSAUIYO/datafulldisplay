package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullTemperature;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullTemperatureService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/temperature")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullTemperatureController {
    private final IFullTemperatureService fullTemperatureService;

    public FullTemperatureController(IFullTemperatureService fullTemperatureService) {
        this.fullTemperatureService = fullTemperatureService;
    }

    /**
     * 处理获取温度记录列表的GET请求
     * 该方法接收一个FullTemperature对象作为参数，用于封装请求参数
     * 返回一个GlobalResult对象，其中包含执行结果
     *
     * @param fullTemperature 请求参数的封装对象，包含了查询温度记录所需的参数
     * @return 返回一个GlobalResult对象，其中包含查询结果
     */
    @GetMapping("/list")
    public GlobalResult list(FullTemperature fullTemperature) {
        // 调用fullTemperatureService的selectFullTemperatureList方法查询温度记录列表
        return fullTemperatureService.selectFullTemperatureList(fullTemperature);
    }

    /**
     * 通过POST请求添加完整的温度数据
     *
     * @param fullTemperature 通过请求体接收的完整温度数据对象
     * @return 返回操作结果的全局状态对象
     */
    @PostMapping
    public GlobalResult add(@RequestBody FullTemperature fullTemperature) {
        return fullTemperatureService.addFullTemperature(fullTemperature);
    }
    /**
     * 获取最新温度信息
     *
     * 该方法通过GET请求访问/latest端点，返回最新的温度信息
     * 主要用于需要获取系统最新录入或测量的温度值的场景
     *
     * @return GlobalResult 包含最新温度信息的全局结果对象
     */
    @GetMapping("/latest")
    public GlobalResult getLatestTemperature() {
        return fullTemperatureService.getLatestTemperature();
    }
}
