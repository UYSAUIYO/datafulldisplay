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
public class FullTemperatureServiceImpl extends ServiceImpl<FullTemperatureMapper, FullTemperature> implements IFullTemperatureService {

    @Autowired
    private FullTemperatureMapper fullTemperatureMapper;
    @Override
    public GlobalResult selectFullTemperatureList(FullTemperature fullTemperature) {
        String local = fullTemperature.getTemperatureLocal();
        LambdaQueryWrapper<FullTemperature> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullTemperature::getTemperatureLocal, local);
        }
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public GlobalResult getLatestTemperature() {
        FullTemperature latestTemperature = fullTemperatureMapper.getLatestTemperature();
        if (latestTemperature != null) {
            return GlobalResult.ok(latestTemperature);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }


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
