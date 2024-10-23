package org.example.datafulldisplay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.AirQuality;

import java.util.List;

public interface AirQualityMapper extends BaseMapper<AirQuality> {
    @Select("SELECT * FROM air_quality WHERE DATE(upload_time) = #{date}")
    List<AirQuality> selectByDate(String date);
}
