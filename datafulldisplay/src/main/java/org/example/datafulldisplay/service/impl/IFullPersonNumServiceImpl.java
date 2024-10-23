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
public class IFullPersonNumServiceImpl extends ServiceImpl<FullPersonSumMapper, FullPersonNum> implements IFullPersonNumService {
    public FullPersonSumMapper personSumMapper;

    @Override
    public Integer totalPersonNum() {
        return personSumMapper.selectSumNum();
    }

    @Override
    public boolean insert(Long personNum) {
        FullPersonNum fullPersonNum = new FullPersonNum();
        fullPersonNum.setNum(personNum);
        return this.save(fullPersonNum);
    }
    @Override
    public GlobalResult getPersonNumList() {
        List<FullPersonNum> personNumList = personSumMapper.selectAll();
        personNumList.forEach(personNum -> {
            System.out.println("Retrieved record: " + personNum);
        });
        return GlobalResult.ok(personNumList);
    }

    @Override
    public GlobalResult getLatestPersonNum() {
        FullPersonNum latestPersonNum = personSumMapper.selectLatest();
        if (latestPersonNum != null) {
            return GlobalResult.ok(latestPersonNum);
        } else {
            return GlobalResult.errorMsg("No data found");
        }
    }
    @Override
    public List<FullPersonNum> getPersonNumListByDate(String date) {
        return personSumMapper.selectByDate(date);
    }
}