package org.example.datafulldisplay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;


@Data
@TableName("author_data")
public class TransitionData {

    @TableId(type = IdType.AUTO)
    private Long numId;

    private String title;

    @TableField(typeHandler = JsonNodeTypeHandler.class)
    private JsonNode content;
}
