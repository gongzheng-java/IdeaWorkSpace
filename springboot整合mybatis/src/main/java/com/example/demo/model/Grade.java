package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;



import java.util.Date;

/**
 * Created by gongzheng on 2019-07-16.
 */
public class Grade {

    private Integer id;

    private String classname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatetime;

    private int del_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", className='" + classname + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", del_flag=" + del_flag +
                '}';
    }
}
