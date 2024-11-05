package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.example.datafulldisplay.domain.FullLight;
import org.example.datafulldisplay.mapper.FullLightMapper;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullLightService;
import org.example.datafulldisplay.service.ws.FireWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 定义一个服务类，用于处理FullLight相关的业务逻辑
@Service
@AllArgsConstructor
public class FullLightServiceImpl extends ServiceImpl<FullLightMapper, FullLight> implements IFullLightService {

    // 注入WebSocket服务器实例，用于后续可能的实时通信
    private FireWebSocketServer webSocketServer;

    // 注入FullLightMapper，用于操作数据库
    @Autowired
    private FullLightMapper fullLightMapper;

    /**
     * 查询FullLight列表
     * 根据FullLight对象中的lightLocal字段进行条件查询
     *
     * @param fullLight 包含查询条件的FullLight对象
     * @return 查询结果封装在GlobalResult中
     */
    @Override
    public GlobalResult selectFullLightList(FullLight fullLight) {
        // 获取查询条件
        String local = fullLight.getLightLocal();
        // 创建查询包装器
        LambdaQueryWrapper<FullLight> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果查询条件不为空，则添加查询条件
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullLight::getLightLocal, local);
        }

        // 执行查询并返回结果
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    /**
     * 添加新的FullLight记录
     * 根据FullLight对象中的lightLocal字段判断是否符合添加条件
     *
     * @param fullLight 要添加的FullLight对象
     * @return 添加结果封装在GlobalResult中
     */
    @Override
    public GlobalResult addFullLight(FullLight fullLight) {
        // 获取添加条件
        String local = fullLight.getLightLocal();
        // 判断是否符合添加条件
        if (local.equals("A") || local.equals("B")) {
            // 符合条件，保存记录
            this.save(fullLight);
            return GlobalResult.ok("新增成功");
        }
        // 不符合条件，返回错误信息
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }

    /**
     * 获取最新的FullLight记录
     * 从数据库中获取最新的FullLight记录，如果没有则返回错误信息
     *
     * @return 最新的FullLight记录封装在GlobalResult中
     */
    @Override
    public GlobalResult getLatestLight() {
        // 从数据库中获取最新的FullLight记录
        FullLight latestLight = fullLightMapper.getLatestLight();
        // 判断是否获取到记录
        if (latestLight != null) {
            // 获取到记录，返回成功
            return GlobalResult.ok(latestLight);
        } else {
            // 未获取到记录，返回错误信息
            return GlobalResult.errorMsg("No data found");
        }
    }
}
