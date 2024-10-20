package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullHumidity;


public interface FullHumidityMapper extends BaseMapper<FullHumidity> {
    @Select("SELECT * FROM full_humidity ORDER BY humidity_id DESC LIMIT 1")
    FullHumidity getLatestHumidity();
}
