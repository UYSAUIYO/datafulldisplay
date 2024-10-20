package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullCoordinate;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullCoordinateService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/coordinate")
public class FullCoordinateController {
    private final IFullCoordinateService fullCoordinateService;

    public FullCoordinateController(IFullCoordinateService fullCoordinateService) {
        this.fullCoordinateService = fullCoordinateService;
    }

    /**
     * 获取坐标列表
     * <p>
     * 该方法通过GET请求处理坐标列表的查询操作它接受一个FullCoordinate对象作为参数，
     * 该对象包含了查询所需的条件信息方法返回一个GlobalResult对象，其中包含了查询结果
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
        return fullCoordinateService.addFullCoordinate(fullCoordinate);
    }

}
