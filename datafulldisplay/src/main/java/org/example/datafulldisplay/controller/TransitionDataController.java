package org.example.datafulldisplay.controller;


import org.example.datafulldisplay.domain.TransitionData;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.TransitionDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transitiondata")
@CrossOrigin("*")
public class TransitionDataController {
    private final TransitionDataService transitionDataService;

    public TransitionDataController(TransitionDataService transitionDataService) {
        this.transitionDataService = transitionDataService;
    }

    /**
     * 获取最新的过渡数据
     *
     * 该方法通过GET请求访问/latest端点，返回系统中最新的过渡数据
     * 主要用于前端获取最新数据以展示在界面上
     *
     * @return GlobalResult 包含最新过渡数据的响应结果
     */
    @GetMapping("/latest")
    public GlobalResult getLatestTransitionData() {
        return transitionDataService.getLatestTransitionData();
    }
    /**
     * 处理添加转换数据的请求
     * 该方法通过POST请求接收TransitionData对象，将其添加到系统中
     *
     * @param transitionData 通过请求体接收的转换数据对象，包含需要添加的数据信息
     * @return 返回GlobalResult对象，包含添加操作的结果信息
     */
    @PostMapping
    public GlobalResult add(@RequestBody TransitionData transitionData){
        return transitionDataService.addTransitionData(transitionData);
    }
}
