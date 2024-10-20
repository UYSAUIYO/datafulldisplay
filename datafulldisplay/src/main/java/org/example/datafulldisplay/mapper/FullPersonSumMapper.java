package org.example.datafulldisplay.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.datafulldisplay.domain.FullPersonNum;


public interface FullPersonSumMapper extends BaseMapper<FullPersonNum> {
    Integer selectSumNum();
}
