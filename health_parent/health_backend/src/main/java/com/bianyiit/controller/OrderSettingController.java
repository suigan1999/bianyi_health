package com.bianyiit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bianyiit.constant.MessageConstant;
import com.bianyiit.entity.Result;
import com.bianyiit.pojo.OrderSetting;
import com.bianyiit.service.OrderSettingService;
import com.bianyiit.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*预约管理*/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;

    /*把上传的excel里面的数据保存到数据库  文件上传，实现预约设置数据批量导入*/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){

        try {
            //使用POI解析表格数据
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //将获取集合由String数组类型转换成OrderSetting
            List<OrderSetting> list=new ArrayList<>();
            for (String[] string : strings) {
            String date=  string[0];
            String number= string[1];
            OrderSetting orderSetting=new OrderSetting(new Date(date),Integer.parseInt(number));
            list.add(orderSetting);
            }
           orderSettingService.add(list);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);

        }
    }

    /*根据月份查询对应的数据对预约设置数据*/
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth (String date){
        /*根据静态页面 可以知道json数据是 集合里面 加may集合（k,v）*/
        try {
            List<Map> list=  orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }
    /*预约设置*/
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return   new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

}
