package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.datafulldisplay.domain.FullCoordinate;


/**
 * FullCoordinateMapper接口扩展了BaseMapper接口，专门用于处理FullCoordinate对象的映射操作。
 * 它提供了一个额外的方法来删除特定坐标ID的FullCoordinate对象。
 */
public interface FullCoordinateMapper extends BaseMapper<FullCoordinate> {
    /**
     * 删除一批FullCoordinate对象。
     *
     * @param coordinateIds 一个包含多个坐标ID的数组，用于指定待删除的FullCoordinate对象。
     * @return 返回被删除的FullCoordinate对象的数量。
     */
    int deleteFullCoordinateByCoordinateIds(Long[] coordinateIds);
}

