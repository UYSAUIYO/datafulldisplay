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

@Service
@AllArgsConstructor
public class FullCoordinateServiceImpl extends ServiceImpl<FullCoordinateMapper, FullCoordinate> implements IFullCoordinateService {

    private CoordinateWebSocketServer webSocketServer;

    @Override
    public GlobalResult selectFullCoordinateList(FullCoordinate fullCoordinate) {
        String local = fullCoordinate.getCoordinateLocal();
        String type = fullCoordinate.getCoordinateType();
        Long x = fullCoordinate.getX();
        Long y = fullCoordinate.getY();
        LambdaQueryWrapper<FullCoordinate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (x != null) {
            lambdaQueryWrapper.eq(FullCoordinate::getX, x);
        }
        if (y != null) {
            lambdaQueryWrapper.eq(FullCoordinate::getY, y);
        }
        if (!local.isEmpty() && local != null) {
            lambdaQueryWrapper.eq(FullCoordinate::getCoordinateLocal, local);
        }
        if (!type.isEmpty() && type != null) {
            lambdaQueryWrapper.eq(FullCoordinate::getCoordinateType, type);
        }
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public GlobalResult addFullCoordinate(FullCoordinate fullCoordinate) throws IOException {
        String local = fullCoordinate.getCoordinateLocal();
        String type = fullCoordinate.getCoordinateType();
        if ((local.equals("A") || local.equals("B")) && (type.equals("ok") || type.equals("warning"))) {
            this.save(fullCoordinate);
            String message = fullCoordinate.toString();
            webSocketServer.sendInfo(message, "admin");
            return GlobalResult.ok("新增成功");
        }
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }
}
