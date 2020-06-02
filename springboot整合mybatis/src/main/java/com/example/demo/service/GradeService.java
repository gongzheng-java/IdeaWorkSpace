package com.example.demo.service;

/**
 * Created by gongzheng on 2020-04-27.
 */

import com.example.demo.mapper.GradeMapper;
import com.example.demo.model.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    public List<Grade> findAll() {
        return gradeMapper.findAll();
    }

    public Grade findOne(Integer id) {
        return gradeMapper.findOne(id);
    }
}
