package org.example.datafulldisplay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "full_light")
public class FullLight implements Serializable {
//    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long lightId;

    private Double light;

    private String lightLocal;

}
