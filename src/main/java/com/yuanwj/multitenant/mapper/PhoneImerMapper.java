package com.yuanwj.multitenant.mapper;

import com.github.condition.MapperCondition;
import com.yuanwj.multitenant.entity.PhoneImei;

import java.util.List;

/**
 * Created with Intellij IDEA
 * Author: xuziling
 * Date:  2019/1/22
 * Description:
 */

public interface PhoneImerMapper {

    PhoneImei findById(Long imeiId);

    List<PhoneImei> findAll();

    List<PhoneImei> findByCondition(MapperCondition condition);

    void insert(PhoneImei phoneImei);
}
