package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.example.datafulldisplay.domain.FullPersonNum;
import org.example.datafulldisplay.mapper.FullPersonSumMapper;
import org.example.datafulldisplay.result.GlobalResult;
import org.example.datafulldisplay.service.IFullPersonNumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/**
 * 实现IFullPersonNumService接口的类，提供与人员数量相关的服务
 * 继承自ServiceImpl，使用FullPersonSumMapper作为数据访问层
 */
public class IFullPersonNumServiceImpl extends ServiceImpl<FullPersonSumMapper, FullPersonNum> implements IFullPersonNumService {
    // FullPersonSumMapper用于数据库访问
    public FullPersonSumMapper personSumMapper;

    /**
     * 获取数据库中所有人员的总数
     *
     * @return 人员总数
     */
    @Override
    public Integer totalPersonNum() {
        return personSumMapper.selectSumNum();
    }

    /**
     * 向数据库中插入新的人员数量记录
     *
     * @param personNum 要插入的人员数量
     * @return 插入成功与否
     */
    @Override
    public boolean insert(Long personNum) {
        FullPersonNum fullPersonNum = new FullPersonNum();
        fullPersonNum.setNum(personNum);
        return this.save(fullPersonNum);
    }

    /**
     * 获取所有人员数量的列表，并打印每条记录
     *
     * @return 包含所有人员数量记录的GlobalResult对象
     */
    @Override
    public GlobalResult getPersonNumList() {
        List<FullPersonNum> personNumList = personSumMapper.selectAll();
        personNumList.forEach(personNum -> {
            System.out.println("Retrieved record: " + personNum);
        });
        return GlobalResult.ok(personNumList);
    }

    /**
     * 获取最新的人员数量记录
     *
     * @return 包含最新人员数量记录的GlobalResult对象，如果没有数据则返回错误信息
     */
    @Override
    public GlobalResult getLatestPersonNum() {
        FullPersonNum latestPersonNum = personSumMapper.selectLatest();
        if (latestPersonNum != null) {
            return GlobalResult.ok(latestPersonNum);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }

    /**
     * 根据日期获取人员数量列表
     *
     * @param date 日期字符串，用于查询
     * @return 匹配日期的人员数量列表，如果没有数据则返回空列表
     */
    @Override
    public List<FullPersonNum> getPersonNumListByDate(String date) {
        return personSumMapper.selectByDate(date);
    }
}
