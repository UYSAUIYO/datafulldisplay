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

    @GetMapping("/latest")
    public GlobalResult getLatestTransitionData() {
        return transitionDataService.getLatestTransitionData();
    }
    @PostMapping
    public GlobalResult add(@RequestBody TransitionData transitionData){
        return transitionDataService.addTransitionData(transitionData);
    }
}
