package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullLight;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/light")
@CrossOrigin(origins = "*")  // 允许来自指定域的跨域请求
public class FullLightController {
    private final IFullLightService fullLightService;

    public FullLightController(IFullLightService fullLightService) {
        this.fullLightService = fullLightService;
    }

    /**
     * 获取全量光线列表
     * <p>
     * 该接口用于获取全量光线的列表信息它接受一个 FullLight 对象作为参数，
     * 该对象可能包含查询条件，如时间范围、地点等信息，用于过滤结果集
     *
     * @param fullLight 包含查询条件的 FullLight 对象，用于指定查询的具体条件
     * @return 返回一个 GlobalResult 对象，其中包含查询结果列表
     */
    @GetMapping("/list")
    public GlobalResult list(FullLight fullLight) {
        return fullLightService.selectFullLightList(fullLight);
    }

    /**
     * 添加满减活动信息
     *
     * 该方法通过POST请求接收满减活动的详细信息，并将其添加到系统中
     * 它期望在请求体中接收一个FullLight对象，该对象包含了满减活动的所有必要信息
     *
     * @param fullLight 满减活动对象，包含活动的详细信息
     * @return 返回一个GlobalResult对象，其中包含了添加操作的结果信息
     */
    @PostMapping
    public GlobalResult add(@RequestBody FullLight fullLight) {
        return fullLightService.addFullLight(fullLight);
    }
    /**
     * 获取最新的灯光信息
     *
     * 该方法通过GET请求访问/latest端点，返回系统中最新的灯光数据
     * 主要用于前端界面在需要获取最新灯光状态时调用
     *
     * @return GlobalResult 包含最新灯光信息的全局结果对象
     */
    @GetMapping("/latest")
    public GlobalResult getLatestLight() {
        return fullLightService.getLatestLight();
    }
}
