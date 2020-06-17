package com.bianyiit.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bianyiit.dao.CheckgroupDao;
import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckGroup;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImp implements CheckgroupService{
    @Autowired
    CheckgroupDao checkgroupDao;


    /*
    * 创建检查组
    * */
    @Override
    public void addCheckgroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkgroupDao.addCheckgroup(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer id = checkGroup.getId();
        this.setCheckGroupAndCheckItem(id,checkitemIds);
    }

    /*
    *
    * 分页*/
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        //先分页后查询
         PageHelper.startPage(currentPage,pageSize);
     Page<CheckGroup> page=   checkgroupDao.selectByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findByid(Integer id) {

      CheckGroup checkGroup=  checkgroupDao.findByid(id);
        return checkGroup;
    }


    /*查询t_checkgroup_checkitem 中与检查组相关联的数据*/
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
     List<Integer>list=   checkgroupDao.findCheckItemIdsByCheckGroupId(id);
        return list;
    }

    @Override
    public void updataCheckGroup(CheckGroup checkGroup, Integer[] id) {
        //修改检查组的基本信息
        checkgroupDao.updataCheckGroup(checkGroup);
        //删除检查组关联的检查项
        checkgroupDao.delectCheckItems(checkGroup.getId());
        //将新的检查项与检查组重新绑定
        this.setCheckGroupAndCheckItem(checkGroup.getId(),id);
    }

    @Override
    public void delectCheckGroup(Integer id) {
        //删除检查组相关联的检查项
        checkgroupDao.delectCheckItems(id);
        //删除检查组
        checkgroupDao.delectCheckGroup(id);
    }

    /*查询检查组*/
    @Override
    public List<CheckGroup> findAll() {
       List<CheckGroup>list= checkgroupDao.findAll();
        return list;
    }

    /*
    * //建立检查组和检查项多对多关系*/
    public void setCheckGroupAndCheckItem(Integer checkGroupID,Integer[] checkitemIds){
        if (checkGroupID!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map=new HashMap<>();
                map.put("checkgroupId",checkGroupID);
                map.put("checkitemId",checkitemId);
                checkgroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
