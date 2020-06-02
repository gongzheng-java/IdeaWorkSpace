package com.example.demo.mapper;

import com.example.demo.model.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gongzheng on 2020-04-27.
 */
@Mapper
public interface GradeMapper {


    /**
     * 注意 注解和xml里面的sql不能同时使用，否则会报错
     * 此处注解必须注释掉，因为xml里面有对应的sql
     * @return
     */
    //@Select("select * from grade")
    public List<Grade> findAll();

    //此处使用了 注解，那么xml里面的sql就要注释掉
   @Select("select * from grade where id=#{id}")
    public Grade findOne(Integer id);


   public Grade findGrade(int id,String classname);
}
