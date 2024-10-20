package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullLight;


public interface FullLightMapper extends BaseMapper<FullLight> {
    @Select("SELECT * FROM full_light ORDER BY light_id DESC LIMIT 1")
    FullLight getLatestLight();

}
