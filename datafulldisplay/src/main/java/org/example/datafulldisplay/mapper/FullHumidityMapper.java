package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullHumidity;


/**
 * FullHumidityMapper接口继承自BaseMapper，专注于处理FullHumidity对象的数据库操作
 * 它定义了一个特定的方法来获取湿度数据表中的最新记录
 */
public interface FullHumidityMapper extends BaseMapper<FullHumidity> {

    /**
     * 使用SQL查询语句从full_humidity表中获取最新的湿度记录
     * 该方法通过湿度ID降序排列并限制结果为1条记录，以确保获取的是最新的数据
     *
     * @return FullHumidity 返回最新的湿度记录对象
     */
    @Select("SELECT * FROM full_humidity ORDER BY humidity_id DESC LIMIT 1")
    FullHumidity getLatestHumidity();
}

