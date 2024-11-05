package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.example.datafulldisplay.domain.FullTemperature;
import org.example.datafulldisplay.mapper.FullTemperatureMapper;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullTemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
/**
 * 实现温度信息的完整服务接口
 */
public class FullTemperatureServiceImpl extends ServiceImpl<FullTemperatureMapper, FullTemperature> implements IFullTemperatureService {

    @Autowired
    private FullTemperatureMapper fullTemperatureMapper;

    /**
     * 根据条件选择温度信息列表
     * @param fullTemperature 包含查询条件的温度信息对象
     * @return 包含查询结果的全局结果对象
     */
    @Override
    public GlobalResult selectFullTemperatureList(FullTemperature fullTemperature) {
        String local = fullTemperature.getTemperatureLocal();
        LambdaQueryWrapper<FullTemperature> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullTemperature::getTemperatureLocal, local);
        }
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    /**
     * 获取最新的温度信息
     * @return 包含最新温度信息的全局结果对象，如果没有数据则返回错误消息
     */
    @Override
    public GlobalResult getLatestTemperature() {
        FullTemperature latestTemperature = fullTemperatureMapper.getLatestTemperature();
        if (latestTemperature != null) {
            return GlobalResult.ok(latestTemperature);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }

    /**
     * 添加新的温度信息
     * 只有当温度信息的地点为"A"或"B"时才允许添加
     * @param fullTemperature 要添加的温度信息对象
     * @return 表示添加结果的全局结果对象
     */
    @Override
    public GlobalResult addFullTemperature(FullTemperature fullTemperature) {
        String local = fullTemperature.getTemperatureLocal();
        if (local.equals("A") || local.equals("B")) {
            this.save(fullTemperature);
            return GlobalResult.ok("新增成功");
        }
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }
}

