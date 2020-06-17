package com.bianyiit.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.alibaba.dubbo.container.page.PageHandler;
import com.bianyiit.dao.CheckItemDao;
import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckItem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CheckItemService.class)////明确当前服务实现的是哪个接口
@Transactional    //开启事务
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
       checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //先分页在查询
        PageHelper.startPage(currentPage,pageSize);

        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List result = page.getResult();

        return new PageResult(total,result);

    }

    @Override
    public void delectByid(Integer id) {
        //先判断要删除的数据有没有关联检查组
     int cont=   checkItemDao.findCountByCheckItemId(id);
     if (cont>0){
         /*
         * 该数据与检查组相关联
         * */
         new RuntimeException();
     }else {
         //能删除执行操作
         checkItemDao.delectByCheckItemId(id);
     }
    }

    @Override
    public CheckItem findCheckItemByid(Integer id) {
        CheckItem checkItem= checkItemDao.findCheckItemByid(id);
        return checkItem;
    }

    @Override
    public void updataCheckItem(CheckItem checkItem) {
        checkItemDao.updataCheckItem(checkItem);
    }

    @Override
    public List<CheckItem> findAllAheckItem() {
        List<CheckItem> list= checkItemDao.findAllAheckItem();
        return list;
    }
}
