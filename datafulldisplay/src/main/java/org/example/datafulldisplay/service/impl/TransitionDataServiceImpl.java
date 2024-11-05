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
    // 注入TransitionDataMapper用于数据操作
    private final TransitionDataMapper transitionDataMapper;

    // 构造方法注入TransitionDataMapper
    public TransitionDataServiceImpl(TransitionDataMapper transitionDataMapper) {
        this.transitionDataMapper = transitionDataMapper;
    }

    /**
     * 获取最新的过渡数据
     *
     * @return 如果找到数据则返回包含数据的GlobalResult，否则返回错误信息
     */
    @Override
    public GlobalResult getLatestTransitionData() {
        TransitionData latestTransitionData = transitionDataMapper.getLatestTransitionData();
        if (latestTransitionData != null) {
            return GlobalResult.ok(latestTransitionData);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }

    /**
     * 添加过渡数据
     *
     * @param transitionData 要添加的过渡数据对象
     * @return 如果数据成功插入数据库则返回包含数据的GlobalResult，否则返回错误信息
     *
     * 该方法首先将过渡数据对象转换为JSON字符串，然后通过WebSocket发送给特定客户端，
     * 最后将数据插入数据库
     */
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
