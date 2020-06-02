package com.example.demo.controller;


import com.example.demo.model.Grade;
import com.example.demo.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gongzheng on 2019-07-20.
 */
@RestController
@RequestMapping("/grade")
public class GradeController  {

    @Autowired
    private GradeService gradeService;



    @RequestMapping("/findAll")
    public List<Grade> findAll() {
        System.out.println("123132");
        return gradeService.findAll();
    }

    @RequestMapping("/findOne")
    public Grade findOne(Integer id) {
        return gradeService.findOne(id);
    }

 

}
