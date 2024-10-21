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

    @GetMapping("/list")
    public GlobalResult list(FullTemperature fullTemperature) {
        return fullTemperatureService.selectFullTemperatureList(fullTemperature);
    }

    @PostMapping
    public GlobalResult add(@RequestBody FullTemperature fullTemperature) {
        return fullTemperatureService.addFullTemperature(fullTemperature);
    }
    @GetMapping("/latest")
    public GlobalResult getLatestTemperature() {
        return fullTemperatureService.getLatestTemperature();
    }
}
