package com.bianyiit.service;

import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckGroup;

import java.util.List;

public interface CheckgroupService {

    void addCheckgroup(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findByid(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void updataCheckGroup(CheckGroup checkGroup, Integer[] id);

    void delectCheckGroup(Integer id);

    List<CheckGroup> findAll();
}
