package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullHumidity;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullHumidityService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/humidity")
public class FullHumidityController {
    private final IFullHumidityService fullHumidityService;

    public FullHumidityController(IFullHumidityService fullHumidityService) {
        this.fullHumidityService = fullHumidityService;
    }

    @GetMapping("/list")
    public GlobalResult list(FullHumidity fullHumidity) {
        return fullHumidityService.selectFullHumidityList(fullHumidity);
    }

    @PostMapping
    public GlobalResult add(@RequestBody FullHumidity fullHumidity) {
        return fullHumidityService.addFullHumidity(fullHumidity);
    }
    @GetMapping("/latest")
    public GlobalResult getLatestHumidity() {
        return fullHumidityService.getLatestHumidity();
    }
}
