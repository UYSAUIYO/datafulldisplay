package org.example.datafulldisplay.service.impl;

import org.example.datafulldisplay.domain.AirQuality;
import org.example.datafulldisplay.mapper.AirQualityMapper;
import org.example.datafulldisplay.service.IAirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirQualityServiceImpl implements IAirQualityService {

    @Autowired
    private AirQualityMapper airQualityMapper;

    @Override
    public void insertAirQuality(Integer aqi) {
        AirQuality airQuality = new AirQuality();
        airQuality.setAqi(aqi);

        // 将 AQI 映射为百分比
        double percentage = mapAqiToPercentage(aqi);
        airQuality.setPercentage(percentage);

        airQualityMapper.insert(airQuality);
    }

    @Override
    public List<AirQuality> getAirQualityByDate(String date) {
        return airQualityMapper.selectByDate(date);
    }

    // 映射 AQI 到百分比的函数
    private double mapAqiToPercentage(int aqi) {
        // 假设 AQI 在 0 到 500 之间
        // 百分比 = (500 - AQI) / 500 * 99 + 1
        // 确保百分比在 1 到 100 之间
        double percentage = (500 - aqi) / 500.0 * 99 + 1;
        return Math.max(1, Math.min(percentage, 100));
    }
}