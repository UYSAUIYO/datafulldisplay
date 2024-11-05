package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullTemperature;

/**
 * FullTemperatureMapper接口继承了BaseMapper<FullTemperature>，用于对full_temperature表进行基本的数据操作
 * 该接口特别关注于获取最新的温度记录，通过提供的SQL查询实现
 */
public interface FullTemperatureMapper extends BaseMapper<FullTemperature> {

    /**
     * 获取full_temperature表中的最新温度记录
     * 该方法通过执行SQL查询来获取最新记录，查询语句按温度记录的ID降序排列并限制结果为1条
     *
     * @return FullTemperature 返回最新的温度记录对象
     */
    @Select("SELECT * FROM full_temperature ORDER BY temperature_id DESC LIMIT 1")
    FullTemperature getLatestTemperature();

}
