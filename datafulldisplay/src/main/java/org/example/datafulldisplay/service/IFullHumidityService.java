package org.example.datafulldisplay.service;


import org.example.datafulldisplay.domain.FullHumidity;
import org.example.datafulldisplay.result.GlobalResult;

public interface IFullHumidityService {

    public GlobalResult selectFullHumidityList(FullHumidity fullHumidity);

    public GlobalResult addFullHumidity(FullHumidity fullHumidity);

}
