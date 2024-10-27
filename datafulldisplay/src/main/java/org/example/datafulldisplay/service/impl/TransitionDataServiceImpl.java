package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.datafulldisplay.domain.TransitionData;
import org.example.datafulldisplay.mapper.TransitionDataMapper;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.TransitionDataService;
import org.example.datafulldisplay.service.ws.TransitionDataWebSocketServer;
import org.springframework.stereotype.Service;

@Service
public class TransitionDataServiceImpl extends ServiceImpl<TransitionDataMapper, TransitionData> implements TransitionDataService {
    private final TransitionDataMapper transitionDataMapper;

    public TransitionDataServiceImpl(TransitionDataMapper transitionDataMapper) {
        this.transitionDataMapper = transitionDataMapper;
    }

    @Override
    public GlobalResult getLatestTransitionData() {
        TransitionData latestTransitionData = transitionDataMapper.getLatestTransitionData();
        if (latestTransitionData != null) {
            return GlobalResult.ok(latestTransitionData);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }

    @SneakyThrows
    @Override
    public GlobalResult addTransitionData(TransitionData transitionData) {
        // 将TransitionData转换为JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(transitionData);

        // 发送WebSocket消息
        TransitionDataWebSocketServer.sendInfo(message, "android");
        System.out.println(message);

        // 插入数据库
        boolean insert = transitionDataMapper.insert(transitionData) > 0;
        if (insert) {
            return GlobalResult.ok(transitionData);
        } else {
            return GlobalResult.errorMsg("Failed to add data");
        }
    }
}