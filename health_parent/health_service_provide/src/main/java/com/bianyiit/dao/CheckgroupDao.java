package com.bianyiit.dao;

import com.bianyiit.pojo.CheckGroup;
import com.bianyiit.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface CheckgroupDao {

    @Insert("  insert into t_checkgroup(code,name,sex,helpCode,remark,attention)\n" +
            "        values\n" +
            "        (#{code},#{name},#{sex},#{helpCode},#{remark},#{attention})\n")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()",keyProperty="id" ,before = false,resultType = Integer.class)

    void addCheckgroup(CheckGroup checkGroup);

    /* <!--设置检查组和检查项多对多关系-->*/
    @Insert("insert into t_checkgroup_checkitem(checkgroup_id,checkitem_id)\n" +
            "        values\n" +
            "        (#{checkgroupId},#{checkitemId})\n")
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    @Select("<script> " +
            "select * from t_checkgroup\n" +
            "    <if test=\"value != null and value.length > 0\">\n" +
            "     where code = #{value} or name = #{value} or helpCode = #{value}\n" +
            "    </if>" +"</script>"+
            "\n")
    Page<CheckGroup> selectByCondition(String queryString);

    /*用来回显编辑中检查组的数据*/
    @Select("select * from t_checkgroup where id=#{id}")
    CheckGroup findByid(Integer id);
 /*  <!--根据检查组ID查询关联的多个检查项ID，查询中间关系表-->*/
    @Select("SELECT checkitem_id FROM t_checkgroup_checkitem WHERE checkgroup_id=#{id} ")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /*修改检查组基本信息*/
    @Update(" <script> " +
            "update t_checkgroup\n" +
            "    <set>\n" +
            "        <if test=\"name != null\">\n" +
            "            name = #{name},\n" +
            "        </if>\n" +
            "        <if test=\"sex != null\">\n" +
            "            sex = #{sex},\n" +
            "        </if>\n" +
            "        <if test=\"code != null\">\n" +
            "            code = #{code},\n" +
            "        </if>\n" +
            "        <if test=\"helpCode != null\">\n" +
            "            helpCode = #{helpCode},\n" +
            "        </if>\n" +
            "        <if test=\"attention != null\">\n" +
            "            attention = #{attention},\n" +
            "        </if>\n" +
            "        <if test=\"remark != null\">\n" +
            "            remark = #{remark}\n" +
            "        </if>\n" +
            "    </set>\n" +
            "    where id = #{id}\n" +
            "</script>")
    void updataCheckGroup(CheckGroup checkGroup);

    /*删除检查组相关联的检查项*/
    @Delete(" delete from t_checkgroup_checkitem where checkgroup_id = #{id}")
    void delectCheckItems(Integer id);

    /*删除检查组*/
    @Delete(" delete from t_checkgroup where id = #{id}")
    void delectCheckGroup(Integer id);

    @Select("select * from t_checkgroup")
    List<CheckGroup> findAll();

    /*多表查询  *根据id查询套餐里面的检查项检查组*/
    @Select("select * from t_checkgroup where id in(\n" +
            "        SELECT checkgroup_id  FROM t_setmeal_checkgroup WHERE setmeal_id =#{id}\n" +
            "    )\n")
    @Results(id ="CheckGroupMap",
            value ={
                    @Result(column = "id",property = "id"),
                    @Result(column = "code",property = "code"),
                    @Result(column = "name",property = "name"),
                    @Result(column = "helpCode",property = "helpCode"),
                    @Result(column = "sex",property = "sex"),
                    @Result(column = "remark",property = "remark"),
                    @Result(column = "attention",property = "attention"),
                    @Result(property = "checkItems",column = "id",many =@Many(
                            select = "com.bianyiit.dao.CheckItemDao.findCheckItemByCheckGroupId"
                    )),
            }
    )
    CheckGroup findCheckGroupBySetmealId(Integer id);
}
