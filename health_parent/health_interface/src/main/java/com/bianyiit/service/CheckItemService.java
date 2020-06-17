package com.bianyiit.service;

import com.bianyiit.entity.PageResult;
import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
 void add(CheckItem checkItem);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void delectByid(Integer id);

    CheckItem findCheckItemByid(Integer id);

    void updataCheckItem(CheckItem checkItem);

    List<CheckItem> findAllAheckItem();
}

