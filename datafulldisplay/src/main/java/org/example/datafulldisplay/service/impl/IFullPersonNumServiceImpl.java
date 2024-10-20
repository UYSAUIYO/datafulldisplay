package org.example.datafulldisplay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.example.datafulldisplay.domain.FullPersonNum;
import org.example.datafulldisplay.mapper.FullPersonSumMapper;
import org.example.datafulldisplay.service.IFullPersonNumService;
import org.springframework.stereotype.Service;

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
}