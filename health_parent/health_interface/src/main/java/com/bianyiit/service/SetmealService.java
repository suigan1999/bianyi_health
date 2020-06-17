package com.bianyiit.service;

import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckGroup;
import com.bianyiit.pojo.OrderSetting;
import com.bianyiit.pojo.Setmeal;

import java.util.List;


public interface SetmealService {
   void add(Setmeal setmeal, Integer[] checkgroupIds) ;

   PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findByid(Integer id);

    List<Integer> findcheckgroupIdsByCheckGroupId(Integer id);

    void updataSetmeal(Setmeal setmeal, Integer[] id);


    List<Setmeal> findAll();

    Setmeal findSetmealById(int id);

    void deleteSetmeal(Integer id);
}
