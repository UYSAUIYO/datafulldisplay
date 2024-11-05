package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullHumidity;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullHumidityService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/humidity")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullHumidityController {
    private final IFullHumidityService fullHumidityService;

    public FullHumidityController(IFullHumidityService fullHumidityService) {
        this.fullHumidityService = fullHumidityService;
    }

    /**
     * 处理获取全湿列表的GET请求
     * 该方法用于接收客户端发送的GET请求，并根据请求参数获取相应的全湿列表信息
     *
     * @param fullHumidity 包含查询条件的FullHumidity对象，用于指定查询列表的条件
     * @return 返回一个GlobalResult对象，其中包含查询结果
     */
    @GetMapping("/list")
    public GlobalResult list(FullHumidity fullHumidity) {
        return fullHumidityService.selectFullHumidityList(fullHumidity);
    }

    /**
     * 处理添加湿度信息的请求
     * 该方法接收一个FullHumidity对象作为请求体，并调用服务层方法addFullHumidity来添加湿度信息
     * 使用@PostMapping注解表明该方法处理POST请求
     *
     * @param fullHumidity 包含湿度信息的FullHumidity对象，由请求体中获取
     * @return 返回一个GlobalResult对象，包含处理结果
     */
    @PostMapping
    public GlobalResult add(@RequestBody FullHumidity fullHumidity) {
        return fullHumidityService.addFullHumidity(fullHumidity);
    }
    /**
     * 获取最新湿度信息
     *
     * 通过GET请求访问/latest端点，返回最新的湿度信息
     * 此方法不接受任何参数
     *
     * @return GlobalResult 包含最新湿度信息的全局结果对象
     */
    @GetMapping("/latest")
    public GlobalResult getLatestHumidity() {
        return fullHumidityService.getLatestHumidity();
    }
}
