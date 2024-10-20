package org.example.datafulldisplay.controller;

import org.example.datafulldisplay.domain.FullLight;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/light")
public class FullLightController {
    private final IFullLightService fullLightService;

    public FullLightController(IFullLightService fullLightService) {
        this.fullLightService = fullLightService;
    }

    @GetMapping("/list")
    public GlobalResult list(FullLight fullLight) {
        return fullLightService.selectFullLightList(fullLight);
    }

    @PostMapping
    public GlobalResult add(@RequestBody FullLight fullLight) {
        return fullLightService.addFullLight(fullLight);
    }
    @GetMapping("/latest")
    public GlobalResult getLatestLight() {
        return fullLightService.getLatestLight();
    }
}
