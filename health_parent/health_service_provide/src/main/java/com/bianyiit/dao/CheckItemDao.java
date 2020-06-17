package com.bianyiit.dao;

import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CheckItemDao {

    @Insert("insert into t_checkitem(code,name,sex,age,price,type,remark,attention)\n" +
            "                      values\n" +
            "        (#{code},#{name},#{sex},#{age},#{price},#{type},#{remark},#{attention})\n")
    void add(CheckItem checkItem);

    @Select("<script> " +
            "select * from t_checkitem\n" +
            "    <if test=\"value != null and value.length > 0\">\n" +
            "        where code = #{value} or name = #{value}\n" +
            "    </if>" +"</script>"+
            "\n")
    Page<CheckItem> selectByCondition(String queryPageBean);

    /*
    * 判断要删除的数据有没有关联检查组
    * */

    @Select("SELECT COUNT(*) FROM t_checkgroup_checkitem  WHERE checkitem_id=#{id}")
    int findCountByCheckItemId(Integer id);

    @Delete("delete from t_checkitem where id = #{id}")
    void delectByCheckItemId(Integer id);

    /*回显编辑项的数据*/
    @Select("select * from t_checkitem where id=#{id}")
    CheckItem findCheckItemByid(Integer id);


    @Update("<script>" +
            "update t_checkitem\n" +
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
            "        <if test=\"age != null\">\n" +
            "            age = #{age},\n" +
            "        </if>\n" +
            "        <if test=\"price != null\">\n" +
            "            price = #{price},\n" +
            "        </if>\n" +
            "        <if test=\"type != null\">\n" +
            "            type = #{type},\n" +
            "        </if>\n" +
            "        <if test=\"attention != null\">\n" +
            "            attention = #{attention},\n" +
            "        </if>\n" +
            "        <if test=\"remark != null\">\n" +
            "            remark = #{remark},\n" +
            "        </if>\n" +
            "    </set>\n" +
            "    where id = #{id}\n"+"</script>"
    )
    void updataCheckItem(CheckItem checkItem);

    @Select("select * from t_checkitem")
    List<CheckItem> findAllAheckItem();
    /*多表查询  *根据id查询套餐里面的检查项检查组*/
   @Select("select * from t_checkitem\n" +
           "      where id in (select checkitem_id from t_checkgroup_checkitem where checkgroup_id=#{id})\n")
   CheckItem findCheckItemByCheckGroupId(Integer id);
}
