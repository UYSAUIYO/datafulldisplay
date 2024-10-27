package org.example.datafulldisplay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.TransitionData;

public interface TransitionDataMapper extends BaseMapper<TransitionData> {
    @Select("SELECT * FROM author_data ORDER BY numId DESC LIMIT 1")
    TransitionData getLatestTransitionData();
}
