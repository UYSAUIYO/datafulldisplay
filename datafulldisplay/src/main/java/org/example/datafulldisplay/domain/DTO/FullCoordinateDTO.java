package org.example.datafulldisplay.domain.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FullCoordinateDTO {
    // Getter 和 Setter 方法
    private int x; // X坐标
    private int y; // Y坐标
    private String type; // 类型，比如 "fire"

    // toString 方法，用于 WebSocket 信息发送
    @Override
    public String toString() {
        return "FullCoordinateDTO{" +
                "x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                '}';
    }
}

