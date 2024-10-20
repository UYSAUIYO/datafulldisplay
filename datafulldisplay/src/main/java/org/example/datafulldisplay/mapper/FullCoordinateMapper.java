package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.datafulldisplay.domain.FullCoordinate;


public interface FullCoordinateMapper extends BaseMapper<FullCoordinate> {
    int deleteFullCoordinateByCoordinateIds(Long[] coordinateIds);
}
