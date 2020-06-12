package com.yuanwj.multitenant.rest;

import com.yuanwj.multitenant.entity.PhoneImei;
import com.yuanwj.multitenant.mapper.PhoneImerMapper;
import com.yuanwj.multitenant.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with Intellij IDEA
 * Author: xuziling
 * Date:  2019/1/25
 * Description:
 */
@RestController
@RequestMapping("/api/v1/")
public class PhoneImeiResource {


    @Resource
    private PhoneImerMapper phoneImerMapper;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(String tenant) {
        UserAuthentication userAuthentication = new UserAuthentication(null,tenant);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        return "租户登录成功";
    }

    @RequestMapping(value = "phoneImei/findAll", method = RequestMethod.GET)
    public List<PhoneImei> findAll() {


        List<PhoneImei> phoneImeis = phoneImerMapper.findAll();

        return phoneImeis;
    }

    @RequestMapping(value = "phoneImei/findById", method = RequestMethod.GET)
    public PhoneImei findById(Long id) {

        PhoneImei phoneImei = phoneImerMapper.findById(id);
        return phoneImei;
    }


    @RequestMapping(value = "add",method = RequestMethod.GET)
    public PhoneImei add() {
        PhoneImei phoneImei = new PhoneImei();
        phoneImei.setRecoredUserId(4l);
        phoneImei.setRecordDate(new Date());
        phoneImei.setImeiNum("sdfs");
        phoneImei.setTenantId(50002l);
        phoneImerMapper.insert(phoneImei);
        return phoneImei;
    }
}
