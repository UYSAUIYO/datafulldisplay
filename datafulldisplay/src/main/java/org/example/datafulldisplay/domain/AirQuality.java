package org.example.datafulldisplay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("air_quality")
public class AirQuality {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer aqi; // 空气质量指数，0-500

    private Double percentage; // 百分比，1-100

    @TableField("upload_time")
    private LocalDateTime uploadTime;
}
