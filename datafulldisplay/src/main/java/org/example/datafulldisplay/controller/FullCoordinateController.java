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

    @GetMapping("/list")
    public GlobalResult list(FullCoordinate fullCoordinate) {
        return fullCoordinateService.selectFullCoordinateList(fullCoordinate);
    }

    @PostMapping
    public GlobalResult add(@RequestBody FullCoordinate fullCoordinate) throws IOException {
        return fullCoordinateService.addFullCoordinate(fullCoordinate);
    }

}
