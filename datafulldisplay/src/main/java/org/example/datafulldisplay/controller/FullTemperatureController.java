package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullTemperature;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/temperature")
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
}
