package org.example.datafulldisplay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.TransitionData;

/**
 * TransitionDataMapper接口扩展了BaseMapper，专门用于处理TransitionData类型的数据库操作
 * 它提供了一个特定的方法来获取最新的过渡数据
 */
public interface TransitionDataMapper extends BaseMapper<TransitionData> {

    /**
     * 获取数据库中最新的过渡数据
     * 该方法通过执行预定义的SQL查询来获取author_data表中的最新数据
     * 它使用num_id字段进行降序排序并限制结果为1，以确保返回的是最新的数据记录
     *
     * @return TransitionData对象，表示数据库中最新的过渡数据如果数据库中没有数据，则返回null
     */
    @Select("SELECT * FROM author_data ORDER BY num_id DESC LIMIT 1")
    TransitionData getLatestTransitionData();
}

