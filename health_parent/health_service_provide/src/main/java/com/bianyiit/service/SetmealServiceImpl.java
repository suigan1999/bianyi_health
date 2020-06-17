package com.bianyiit.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.bianyiit.constant.RedisConstant;
import com.bianyiit.dao.SetmealDao;
import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckGroup;
import com.bianyiit.pojo.OrderSetting;
import com.bianyiit.pojo.Setmeal;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;
    @Autowired
    JedisPool jedisPool;


    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        /*添加检查套餐*/
        setmealDao.add(setmeal);
        /**/
        Integer id = setmeal.getId();
        this.setSetmealAndCheckGroup(id,checkgroupIds);
        String fileName = setmeal.getImg();

            /*将保存在mysql数据库中的图片名称同样保存在redis数据库中*/
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);




    }

    /*分页*/
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        /*先分页后查询*/
        PageHelper.startPage(currentPage,pageSize);
     Page<Setmeal> page= setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /*回显编辑框数据*/
    @Override
    public Setmeal findByid(Integer id) {
        Setmeal   setmeal= setmealDao.dindaByid(id);
        return setmeal;
    }

    @Override
    public List<Integer> findcheckgroupIdsByCheckGroupId(Integer id) {
      List<Integer> list= setmealDao.findcheckgroupIdsByCheckGroupId(id);
        return list;
    }
    /*修改检查套餐*/
    @Override
    public void updataSetmeal(Setmeal setmeal, Integer[] id) {
        //修改套餐的基本信息
        System.out.println(setmeal.getImg()+"这是图片2");
        setmealDao.updataSetmeal(setmeal);

        //删除套餐相关联的检查组
        setmealDao.deleteSetmealCheckGroupByid(setmeal.getId());
        //将新的检查组与套餐相关联
        this.setSetmealAndCheckGroup(setmeal.getId(),id);
    }

    /*查询所有套餐*/
    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list=  setmealDao.findAll();
        return list;
    }

    /*根据id查询套餐里面的检查项检查组*/
    @Override
    public Setmeal findSetmealById(int id) {
      Setmeal setmeal=  setmealDao.findSetmealById(id);
        return setmeal;
    }

    /*删除套餐*/
    @Override
    public void deleteSetmeal(Integer id) {
        //删除与套餐相关联的检查组
        setmealDao.deleteSetmealCheckGroupByid(id);
        //删除套餐
        setmealDao.deleteSetmeal(id);
    }




    /*将检查套餐与检查组相关联*/
    public void setSetmealAndCheckGroup(Integer setmealID,Integer[] checkgroupIds){
        if (setmealID!=null&&checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
               setmealDao.setSetmealAndCheckGroup(setmealID,checkgroupId);
            }
        }
    }
}
