package com.bianyiit.dao;

import com.bianyiit.entity.QueryPageBean;
import com.bianyiit.pojo.CheckGroup;
import com.bianyiit.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SetmealDao {

    @Insert("insert into t_setmeal(code,name,sex,helpCode,remark,attention,age,price,img)\n" +
            "                      values\n" +
            "        (#{code},#{name},#{sex},#{helpCode},#{remark},#{attention},#{age},#{price},#{img})\n")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()",keyProperty="id" ,before = false,resultType = Integer.class)
    void add(Setmeal setmeal);

    @Insert("insert into t_setmeal_checkgroup(setmeal_id,checkgroup_id) values(#{param1},#{param2})")/*param1(参数1)param1(参数2)*/
    void setSetmealAndCheckGroup(Integer setmealID, Integer checkgroupId);

    @Select("<script> " +
            "select * from t_setmeal\n" +
            "    <if test=\"value != null and value.length > 0\">\n" +
            "     where code = #{value} or name = #{value} or helpCode = #{value}\n" +
            "    </if>" +"</script>"+
            "\n")
    Page<Setmeal> findPage(String queryString);

    /*回显编辑框数据*/
    @Select("select * from t_setmeal where id=#{id}")
    Setmeal dindaByid(Integer id);

    /*回显复选框*/
    @Select("SELECT checkgroup_id FROM t_setmeal_checkgroup WHERE setmeal_id=#{id}")
    List<Integer> findcheckgroupIdsByCheckGroupId(Integer id);

    /*修改套餐基本信息  set标记是mybatis提供的一个智能标记，我一般将其用在修改的sql中*/
    @Update(" <script> " +
            "update t_setmeal\n" +
            "    <set>\n" +
            "        <if test=\"name != null\">\n" +
            "            name = #{name},\n" +
            "        </if>\n" +
            "        <if test=\"code != null\">\n" +
            "            code = #{code},\n" +
            "        </if>\n" +
            "        <if test=\"helpCode != null\">\n" +
            "            helpCode = #{helpCode},\n" +
            "        </if>\n" +
            "        <if test=\"sex != null\">\n" +
            "            sex = #{sex},\n" +
            "        </if>\n" +
            "        <if test=\"age != null\">\n" +
            "            age = #{age},\n" +
            "        </if>\n" +
            "        <if test=\"price != null\">\n" +
            "            price = #{price},\n" +
            "        </if>\n" +
            "        <if test=\"remark != null\">\n" +
            "            remark = #{remark},\n" +
            "        </if>\n" +
            "        <if test=\"attention != null\">\n" +
            "            attention = #{attention},\n" +
            "        </if>\n" +
            "        <if test=\"img != null\">\n" +
            "            img = #{img},\n" +
            "        </if>\n" +
            "    </set>\n" +
            "    where id = #{id}\n" +
            "</script>")
    void updataSetmeal(Setmeal setmeal);
    /*删除套餐相关联的检查项*/
    @Delete(" delete from t_setmeal_checkgroup where setmeal_id = #{id}")
    void deleteSetmealCheckGroupByid(Integer id);

    /*查询所有套餐*/
    @Select("select * from t_setmeal")
    List<Setmeal> findAll();

    /*多表查询  *根据id查询套餐里面的检查项检查组*/

    @Select("select * from t_setmeal where id=#{id}")
    @Results(id ="SetmealMap",
    value ={
            @Result(column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "code",property = "code"),
            @Result(column = "helpCode",property = "helpCode"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "age",property = "age"),
            @Result(column = "price",property = "price"),
            @Result(column = "remark",property = "remark"),
            @Result(column = "attention",property = "attention"),
            @Result(column = "img",property = "img"),
            @Result(property = "checkGroups",column = "id",many =@Many(
                    select = "com.bianyiit.dao.CheckgroupDao.findCheckGroupBySetmealId"
            )),
            /*1对多用one 多对多用many*/
    }
    )
    Setmeal findSetmealById(Integer id);

    /*删除套餐*/
    @Delete("delete from t_setmeal where id=#{id}")
    void deleteSetmeal(Integer id);
}
