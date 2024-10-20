package org.example.datafulldisplay.service;


import org.example.datafulldisplay.domain.FullTemperature;
import org.example.datafulldisplay.result.GlobalResult;

public interface IFullTemperatureService {

    GlobalResult selectFullTemperatureList(FullTemperature fullTemperature);

    GlobalResult addFullTemperature(FullTemperature fullCoordinate);
    GlobalResult getLatestTemperature();


}
