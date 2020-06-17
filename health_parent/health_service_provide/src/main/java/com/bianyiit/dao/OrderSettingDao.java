package com.bianyiit.dao;

import com.bianyiit.pojo.OrderSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    @Select("select count(*) from t_ordersetting where orderDate =#{orderDate} ")
    int findCountByOrderDate(Date orderDate);

    @Update("update t_ordersetting\n" +
            "          set number = #{number}\n" +
            "          where orderDate = #{orderDate}\n")
    void updataByOrderDate(OrderSetting orderSetting);

    @Insert("insert into t_ordersetting\n" +
            "          (orderDate,number,reservations)\n" +
            "                      values\n" +
            "          (#{orderDate},#{number},#{reservations})\n")
    void add(OrderSetting orderSetting);

    /*获取当前月数据*/
    @Select("select * from t_ordersetting where orderDate between #{param1} and #{param2}  ")
    List<OrderSetting> getOrderSettingByMonth(String begin, String end);
}
