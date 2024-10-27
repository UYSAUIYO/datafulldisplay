package org.example.datafulldisplay.service;

import org.example.datafulldisplay.domain.AirQuality;

import java.util.List;

public interface IAirQualityService {
    void insertAirQuality(Integer aqi);

    List<AirQuality> getAirQualityByDate(String date);

    List<AirQuality> getAirQualityByTwoDates(String date1, String date2);
}
