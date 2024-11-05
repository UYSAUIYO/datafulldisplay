package org.example.datafulldisplay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.AirQuality;

import java.util.List;

/**
 * AirQualityMapper接口继承自BaseMapper，专注于处理空气质量数据的数据库操作
 * 它提供了根据特定日期选择空气质量记录的功能
 */
public interface AirQualityMapper extends BaseMapper<AirQuality> {

    /**
     * 根据单个日期选择空气质量记录
     * 此方法执行一个SQL查询，选择上传时间与指定日期匹配的所有空气质量记录
     *
     * @param date 用于查询的日期，格式为"YYYY-MM-DD"
     * @return 匹配指定日期的所有空气质量记录列表
     */
    @Select("SELECT * FROM air_quality WHERE DATE(upload_time) = #{date}")
    List<AirQuality> selectByDate(String date);

    /**
     * 根据两个日期选择空气质量记录
     * 此方法执行一个SQL查询，选择上传时间与两个指定日期中的任意一天匹配的所有空气质量记录
     *
     * @param date1 第一个查询日期，格式为"YYYY-MM-DD"
     * @param date2 第二个查询日期，格式为"YYYY-MM-DD"
     * @return 匹配指定两个日期中的任一日期的所有空气质量记录列表
     */
    @Select("SELECT * FROM air_quality WHERE DATE(upload_time) = #{date1} OR DATE(upload_time) = #{date2}")
    List<AirQuality> selectByTwoDates(String date1, String date2);
}

