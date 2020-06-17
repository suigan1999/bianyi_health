package com.bianyiit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bianyiit.constant.MessageConstant;
import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.entity.Result;
import com.bianyiit.pojo.CheckItem;
import com.bianyiit.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
  public static   List<String> array=new ArrayList();

    @Reference
    CheckItemService checkItemService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        System.out.println("----------------------");
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }



    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       /* String queryString = queryPageBean.getQueryString();
        if (array.size()==0||array.isEmpty()||array==null&&!queryString.isEmpty()){
            array.add(queryPageBean.getQueryString());
        }*//*if (!array.get(0).equals(queryPageBean.getQueryString())){
            array.set(0,queryPageBean.getQueryString());
            queryPageBean.setCurrentPage(1);
        }*//*

        System.out.println(queryString);

        if (!queryString.isEmpty()&&!array.get(0).equals(queryString)){
            array.set(0,queryString);
            queryPageBean.setCurrentPage(1);
        }*/

        PageResult pageResult = checkItemService.pageQuery(queryPageBean);

        return pageResult;
    }
    /*
    * 删除数据
    * */
    @RequestMapping("/delete")
    public Result delectCheckItem(Integer id){
        try {
            checkItemService.delectByid(id);
        } catch (Exception e) {
            e.printStackTrace();
            new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }

       return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /*
    *回显编辑项
     */
    @RequestMapping("/findById")
    public Result findByid(Integer id){
        try {
            CheckItem checkItem  =  checkItemService.findCheckItemByid(id);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);

        }
    }

    /*
    * 修改
    * */
    @RequestMapping("/edit")
    public Result updataCheckItem(@RequestBody CheckItem checkItem){
        try{
            checkItemService.updataCheckItem(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }

    /*
    * 为检查组查询所有检查项
    * */
    @RequestMapping("/findAll")
    public Result findAllAheckItem(){

        try {
            List<CheckItem> list=  checkItemService.findAllAheckItem();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}
