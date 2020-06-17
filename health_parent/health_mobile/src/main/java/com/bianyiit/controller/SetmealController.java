package com.bianyiit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bianyiit.constant.MessageConstant;
import com.bianyiit.entity.Result;
import com.bianyiit.pojo.Setmeal;
import com.bianyiit.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    SetmealService setmealService;

    /*查询所有套餐*/
    @RequestMapping("/getAllSetmeal")
    public Result getAllSetmeal(){
        try {
            List<Setmeal>  list=setmealService.findAll();
            return  new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS);

        }
    }

    /*根据id查询套餐里面的检查项检查组*/
    @RequestMapping("/findById")
    public Result findById(int id){
        try {
            Setmeal setmeal=  setmealService.findSetmealById(id);
                return  new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS);
        }
    }
}
