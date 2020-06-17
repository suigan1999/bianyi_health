package com.bianyiit.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bianyiit.dao.OrderSettingDao;
import com.bianyiit.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass =OrderSettingService.class )
public class OrderSettingServiceImpl implements OrderSettingService{
    @Autowired
    OrderSettingDao orderSettingDao;

    /*将上传的excel的数据保存到数据库*/
    @Override
    public void add(List<OrderSetting> list) {
       if (list!=null&&list.size()>0){
           for (OrderSetting orderSetting : list) {
               //判断数据库是否有对应的日期
          int count  =  orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
          /*如果大于0说明里面有这个数据 进行覆盖  反之没有进行添加*/
          if (count >0){
              orderSettingDao.updataByOrderDate(orderSetting);
          }else {
              orderSettingDao.add(orderSetting);
          }

           }
       }
    }

    //根据月份获取对应的 数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String begin=date+"-1";
        String end=date+"-31";
        //调用dao层获取数据
    List<OrderSetting> orderSettings= orderSettingDao.getOrderSettingByMonth(begin,end);
    List<Map>  list=new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map<String,Object> map=new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());//获取日期数字（几号）
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            list.add(map);
        }

        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //判断数据库是否有对应的日期
        int count  =  orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        /*如果大于0说明里面有这个数据 进行覆盖  反之没有进行添加*/
        if (count >0){
            orderSettingDao.updataByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
