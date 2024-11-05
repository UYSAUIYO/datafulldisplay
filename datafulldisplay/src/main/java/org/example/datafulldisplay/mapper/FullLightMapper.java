package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullLight;


/**
 * FullLightMapper接口继承自BaseMapper，专门用于处理FullLight对象的数据库操作
 * 它定义了一个特殊的方法来获取最新的光照信息记录
 */
public interface FullLightMapper extends BaseMapper<FullLight> {

    /**
     * 获取数据库中最新的光照信息记录
     *
     * 此方法通过执行SQL查询来获取full_light表中的最新记录
     * 它根据light_id字段进行降序排序，并限制结果返回第一条记录
     *
     * @return FullLight 返回最新的光照信息对象
     */
    @Select("SELECT * FROM full_light ORDER BY light_id DESC LIMIT 1")
    FullLight getLatestLight();

}

