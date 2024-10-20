package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.example.datafulldisplay.domain.FullHumidity;
import org.example.datafulldisplay.mapper.FullHumidityMapper;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullHumidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FullHumidityServiceImpl extends ServiceImpl<FullHumidityMapper, FullHumidity> implements IFullHumidityService {

    @Autowired
    private FullHumidityMapper fullHumidityMapper;
    @Override
    public GlobalResult selectFullHumidityList(FullHumidity fullHumidity) {
        String local = fullHumidity.getHumidityLocal();
        LambdaQueryWrapper<FullHumidity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullHumidity::getHumidityLocal, local);
        }
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public GlobalResult addFullHumidity(FullHumidity fullHumidity) {
        String local = fullHumidity.getHumidityLocal();
        if (local.equals("A") || local.equals("B")) {
            this.save(fullHumidity);
            return GlobalResult.ok("新增成功");
        }
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }
    @Override
    public GlobalResult getLatestHumidity() {
        FullHumidity latestHumidity = fullHumidityMapper.getLatestHumidity();
        if (latestHumidity != null) {
            return GlobalResult.ok(latestHumidity);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }
}
