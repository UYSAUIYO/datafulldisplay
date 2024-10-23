package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.datafulldisplay.domain.FullPersonNum;

import java.util.List;


public interface FullPersonSumMapper extends BaseMapper<FullPersonNum> {
    Integer selectSumNum();
    // 获取所有记录，按上传时间排序
    @Select("SELECT * FROM full_person_num ORDER BY upload_time")
    List<FullPersonNum> selectAll();

    // 获取最新的一条记录
    @Select("SELECT * FROM full_person_num ORDER BY upload_time DESC LIMIT 1")
    FullPersonNum selectLatest();

    // 查询指定日期的人流数据
    @Select("SELECT * FROM full_person_num WHERE DATE(upload_time) = #{date}")
    List<FullPersonNum> selectByDate(@Param("date") String date);

    // 查询指定日期范围的人流数据（如果需要）
    @Select("SELECT * FROM full_person_num WHERE DATE(upload_time) BETWEEN #{startDate} AND #{endDate}")
    List<FullPersonNum> selectByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
