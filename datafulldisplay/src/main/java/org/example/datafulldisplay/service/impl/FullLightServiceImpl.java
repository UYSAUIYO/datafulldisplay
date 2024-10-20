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

@Service
@AllArgsConstructor
public class FullLightServiceImpl extends ServiceImpl<FullLightMapper, FullLight> implements IFullLightService {

    private FireWebSocketServer webSocketServer;
    @Autowired
    private FullLightMapper fullLightMapper;
    @Override
    public GlobalResult selectFullLightList(FullLight fullLight) {
        String local = fullLight.getLightLocal();
        LambdaQueryWrapper<FullLight> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullLight::getLightLocal, local);
        }

        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public GlobalResult addFullLight(FullLight fullLight) {
        String local = fullLight.getLightLocal();
        if (local.equals("A") || local.equals("B")) {
            this.save(fullLight);
            return GlobalResult.ok("新增成功");
        }
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }
    @Override
    public GlobalResult getLatestLight() {
        FullLight latestLight = fullLightMapper.getLatestLight();
        if (latestLight != null) {
            return GlobalResult.ok(latestLight);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }
}
