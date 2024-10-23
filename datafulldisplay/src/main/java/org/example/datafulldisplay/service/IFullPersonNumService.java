package org.example.datafulldisplay.service;


import org.example.datafulldisplay.domain.FullPersonNum;
import org.example.datafulldisplay.result.GlobalResult;

import java.util.List;

public interface IFullPersonNumService {

    public Integer totalPersonNum();

    public boolean insert(Long personNum);
    // 新增：获取识别人数的列表
    GlobalResult getPersonNumList();

    // 新增：获取最新的识别人数
    GlobalResult getLatestPersonNum();
    List<FullPersonNum> getPersonNumListByDate(String date);

}
