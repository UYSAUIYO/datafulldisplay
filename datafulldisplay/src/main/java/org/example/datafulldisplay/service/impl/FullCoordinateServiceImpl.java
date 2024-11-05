package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.example.datafulldisplay.domain.FullCoordinate;
import org.example.datafulldisplay.mapper.FullCoordinateMapper;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullCoordinateService;
import org.example.datafulldisplay.service.ws.CoordinateWebSocketServer;
import org.springframework.stereotype.Service;

import java.io.IOException;

// 定义一个服务类，用于处理完整的坐标信息
@Service
@AllArgsConstructor
public class FullCoordinateServiceImpl extends ServiceImpl<FullCoordinateMapper, FullCoordinate> implements IFullCoordinateService {

    // 注入WebSocket服务器实例，用于坐标信息的实时通信
    private CoordinateWebSocketServer webSocketServer;

    /**
     * 查询完整的坐标列表
     * 根据给定的坐标参数，选择性地匹配并返回坐标列表
     *
     * @param fullCoordinate 包含查询条件的坐标对象，如位置、类型、X和Y坐标
     * @return 包含查询结果的GlobalResult对象
     */
    @Override
    public GlobalResult selectFullCoordinateList(FullCoordinate fullCoordinate) {
        // 获取坐标的局部信息
        String local = fullCoordinate.getCoordinateLocal();
        // 获取坐标的类型信息
        String type = fullCoordinate.getCoordinateType();
        // 获取X坐标
        Long x = fullCoordinate.getX();
        // 获取Y坐标
        Long y = fullCoordinate.getY();
        // 创建一个Lambda查询包装器，用于构建查询条件
        LambdaQueryWrapper<FullCoordinate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果X坐标不为空，则添加X坐标的查询条件
        if (x != null) {
            lambdaQueryWrapper.eq(FullCoordinate::getX, x);
        }
        // 如果Y坐标不为空，则添加Y坐标的查询条件
        if (y != null) {
            lambdaQueryWrapper.eq(FullCoordinate::getY, y);
        }
        // 如果局部信息不为空且非空字符串，则添加局部信息的查询条件
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullCoordinate::getCoordinateLocal, local);
        }
        // 如果类型信息不为空且非空字符串，则添加类型信息的查询条件
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullCoordinate::getCoordinateType, type);
        }
        // 返回查询结果，封装在GlobalResult对象中
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    /**
     * 添加一个新的完整坐标信息
     * 验证坐标信息的合法性，并保存合法的坐标信息
     *
     * @param fullCoordinate 待添加的坐标对象
     * @return 添加结果，封装在GlobalResult对象中
     * @throws IOException 如果保存过程中发生IO异常
     */
    @Override
    public GlobalResult addFullCoordinate(FullCoordinate fullCoordinate) throws IOException {
        // 获取坐标的局部信息
        String local = fullCoordinate.getCoordinateLocal();
        // 获取坐标的类型信息
        String type = fullCoordinate.getCoordinateType();
        // 验证局部信息和类型信息的合法性
        if ((local.equals("A") || local.equals("B")) && (type.equals("ok") || type.equals("warning"))) {
            // 保存坐标信息
            this.save(fullCoordinate);
            // 将坐标信息转换为字符串形式
            String message = fullCoordinate.toString();
            // 通过WebSocket发送坐标信息
            CoordinateWebSocketServer.sendInfo(message);
            // 返回成功消息
            return GlobalResult.ok("新增成功");
        }
        // 如果坐标信息不合法，返回错误消息
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }
}
