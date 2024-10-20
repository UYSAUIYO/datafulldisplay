package org.example.datafulldisplay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "full_person_num")
@Data
public class FullPersonNum implements Serializable {
//    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long numId;

    private Long num;

}
