package com.bianyiit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bianyiit.constant.MessageConstant;
import com.bianyiit.constant.RedisConstant;
import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.entity.Result;
import com.bianyiit.pojo.CheckGroup;
import com.bianyiit.pojo.OrderSetting;
import com.bianyiit.pojo.Setmeal;
import com.bianyiit.service.SetmealService;
import com.bianyiit.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;
    @Autowired
    JedisPool jedisPool;

    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {


            System.out.println(imgFile);
            //获取原始的文件上传的名字,主要用来截取文件后缀
            String originalFilename = imgFile.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String extention = originalFilename.substring(index - 1);//.jpg
            String fileName = UUID.randomUUID().toString() + extention;//文件上传之后的名称
            try {
                QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);

                //将图片上传的名称保存到redis数据库,基于redis的set集合存储
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);

            }
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);




    }

    /*添加*/
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);

        } catch (Exception e) {
            e.printStackTrace();
          return   new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return   new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    /*分页*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult pageResult = setmealService.findPage(queryPageBean);
        return pageResult;
    }
    /*回显编辑数据*/
    @RequestMapping("/findById")
    public Result findByid(Integer id){
        try {
            Setmeal setmeal= setmealService.findByid(id);
            return   new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }

    /*根据检查组ID查询当前检查组包含的检查项ID，*/
    @RequestMapping("/findcheckgroupIdsByCheckGroupId")
    public Result  findcheckgroupIdsByCheckGroupId(Integer id){
        try {
            List<Integer> list=  setmealService.findcheckgroupIdsByCheckGroupId(id);
            return   new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);

        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /*修改检查组*/
    @RequestMapping("/edit")
    public Result updataSetmeal(@RequestBody Setmeal setmeal,@RequestParam("checkgroupIds") Integer[] id){
        try {
            setmealService.updataSetmeal(setmeal,id);
          /*  System.out.println(setmeal.getImg()+"这是图片");*/
            return   new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(true,MessageConstant.EDIT_SETMEAL_FAIL);
        }

    }

    /*删除套餐*/
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.deleteSetmeal(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }


    }

}


