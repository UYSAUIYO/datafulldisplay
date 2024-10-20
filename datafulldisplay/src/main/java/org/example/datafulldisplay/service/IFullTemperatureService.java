package org.example.datafulldisplay.service;


import org.example.datafulldisplay.domain.FullTemperature;
import org.example.datafulldisplay.result.GlobalResult;

public interface IFullTemperatureService {

    public GlobalResult selectFullTemperatureList(FullTemperature fullTemperature);

    public GlobalResult addFullTemperature(FullTemperature fullCoordinate);

}
