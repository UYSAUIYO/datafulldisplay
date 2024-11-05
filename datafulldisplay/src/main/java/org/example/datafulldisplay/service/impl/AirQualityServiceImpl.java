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

    /**
     * 插入空气质量数据
     *
     * @param aqi 空气质量指数
     */
    @Override
    public void insertAirQuality(Integer aqi) {
        AirQuality airQuality = new AirQuality();
        airQuality.setAqi(aqi);

        // 将 AQI 映射为百分比
        double percentage = mapAqiToPercentage(aqi);
        airQuality.setPercentage(percentage);

        airQualityMapper.insert(airQuality);
    }

    /**
     * 根据日期获取空气质量数据
     *
     * @param date 日期
     * @return 空气质量数据列表
     */
    @Override
    public List<AirQuality> getAirQualityByDate(String date) {
        return airQualityMapper.selectByDate(date);
    }

    /**
     * 根据两个日期获取空气质量数据
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 空气质量数据列表
     */
    @Override
    public List<AirQuality> getAirQualityByTwoDates(String date1, String date2) {
        return airQualityMapper.selectByTwoDates(date1, date2);
    }

    /**
     * 映射 AQI 到百分比的函数
     *
     * @param aqi 空气质量指数
     * @return 对应的百分比
     */
    private double mapAqiToPercentage(int aqi) {
        // 假设 AQI 在 0 到 500 之间
        // 百分比 = (500 - AQI) / 500 * 99 + 1
        // 确保百分比在 1 到 100 之间
        double percentage = (500 - aqi) / 500.0 * 99 + 1;
        return Math.max(1, Math.min(percentage, 100));
    }
}
