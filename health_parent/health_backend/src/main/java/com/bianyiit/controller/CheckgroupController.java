package com.bianyiit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bianyiit.constant.MessageConstant;
import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.entity.Result;
import com.bianyiit.pojo.CheckGroup;
import com.bianyiit.service.CheckgroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    CheckgroupService checkgroupService;

     @RequestMapping("/add")
    public Result addCheckgroup(@RequestBody CheckGroup checkGroup ,Integer[] checkitemIds){
         try {
             checkgroupService.addCheckgroup(checkGroup,checkitemIds);
             return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

         } catch (Exception e) {
             e.printStackTrace();
             return new Result(true, MessageConstant.ADD_CHECKGROUP_FAIL);

         }

     }

     /*
     *
     * 分页*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult= checkgroupService.findPage(queryPageBean);
        return  pageResult;
    }

    /*回显数据*/
   @RequestMapping("/findById")
    public Result findByid(Integer id){

       try {
           CheckGroup  checkGroup=   checkgroupService.findByid(id);
           return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
       } catch (Exception e) {
           return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);

       }
   }

   /*回显复选框*/
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try {
     List<Integer> list=checkgroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);

        }
    }


    /*修改检查组*/
    @RequestMapping("/edit")
    public Result updataCheckGroup(@RequestBody CheckGroup checkGroup,@RequestParam("checkitemIds") Integer[] id){
        try {
            checkgroupService.updataCheckGroup(checkGroup,id);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);

        }
    }

    /*删除检查组*/
    @RequestMapping("/delete")
    public Result delectCheckGroup(Integer id){
        try {
            checkgroupService.delectCheckGroup(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);

        }
    }
    /*查询所有检查组回显数据*/
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> list=checkgroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

}
