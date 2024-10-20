package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullTemperature;


public interface FullTemperatureMapper extends BaseMapper<FullTemperature> {
    @Select("SELECT * FROM full_temperature ORDER BY temperature_id DESC LIMIT 1")
    FullTemperature getLatestTemperature();

}
