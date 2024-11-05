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
/**
 * 实现全面湿度服务接口，提供湿度数据的操作
 */
public class FullHumidityServiceImpl extends ServiceImpl<FullHumidityMapper, FullHumidity> implements IFullHumidityService {

    @Autowired
    private FullHumidityMapper fullHumidityMapper;

    /**
     * 根据条件查询全面湿度列表
     *
     * @param fullHumidity 包含查询条件的湿度对象
     * @return 查询结果封装在GlobalResult中
     */
    @Override
    public GlobalResult selectFullHumidityList(FullHumidity fullHumidity) {
        String local = fullHumidity.getHumidityLocal();
        LambdaQueryWrapper<FullHumidity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (local != null && !local.isEmpty()) {
            lambdaQueryWrapper.eq(FullHumidity::getHumidityLocal, local);
        }
        return GlobalResult.ok(this.baseMapper.selectList(lambdaQueryWrapper));
    }

    /**
     * 添加新的全面湿度记录
     *
     * @param fullHumidity 要添加的湿度对象
     * @return 添加结果封装在GlobalResult中
     */
    @Override
    public GlobalResult addFullHumidity(FullHumidity fullHumidity) {
        String local = fullHumidity.getHumidityLocal();
        if (local.equals("A") || local.equals("B")) {
            this.save(fullHumidity);
            return GlobalResult.ok("新增成功");
        }
        return GlobalResult.errorMsg("新增失败，可能原因：local或type不合法");
    }

    /**
     * 获取最新的湿度记录
     *
     * @return 最新的湿度记录封装在GlobalResult中，如果没有数据则返回错误信息
     */
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

