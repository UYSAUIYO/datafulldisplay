package org.example.datafulldisplay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启WebSocket支持
 */
@Configuration
public class WebSocketConfig {

    /**
     * 创建并配置一个ServerEndpointExporter Bean
     *
     * ServerEndpointExporter的作用是将标记了@ServerEndpoint的类自动注册为WebSocket的端点
     * 通过Spring的配置机制，自动扫描并注册这些端点，简化了WebSocket端点的配置过程
     *
     * @return ServerEndpointExporter实例，用于自动注册WebSocket端点
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

} 
