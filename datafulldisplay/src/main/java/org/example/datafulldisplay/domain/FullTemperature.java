package org.example.datafulldisplay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "full_temperature")
@Data
public class FullTemperature implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long temperatureId;

    private Double tem;

    private String temperatureLocal;

}
