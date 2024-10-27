package org.example.datafulldisplay.service;

import org.example.datafulldisplay.domain.TransitionData;
import org.example.datafulldisplay.result.GlobalResult;

public interface TransitionDataService {
    GlobalResult getLatestTransitionData();

    GlobalResult addTransitionData(TransitionData transitionData);
}
