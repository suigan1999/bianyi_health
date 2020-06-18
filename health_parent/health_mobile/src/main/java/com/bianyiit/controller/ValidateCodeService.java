package com.bianyiit.controller;

import com.aliyuncs.exceptions.ClientException;
import com.bianyiit.constant.MessageConstant;
import com.bianyiit.constant.RedisMessageConstant;
import com.bianyiit.entity.Result;
import com.bianyiit.utils.SMSUtils;
import com.bianyiit.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeService {

    @Autowired
    JedisPool jedisPool;
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
//用工具类随机生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        System.out.println(code);
        //将验证码发送到手机上
        try {

           // SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
             } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码保存到redis中   5分钟后清理验证码信息
        jedisPool.getResource().setex(telephone+RedisMessageConstant.VALIDATECODE_PIC_RESOURCES,5*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }

}
