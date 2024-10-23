package org.example.datafulldisplay.service;

import org.example.datafulldisplay.domain.AirQuality;

import java.util.List;

public interface IAirQualityService {
    void insertAirQuality(Integer aqi);

    List<AirQuality> getAirQualityByDate(String date);
}
