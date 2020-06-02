package com.example.demo.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by gongzheng on 2020-05-10.
 */
@WebService(serviceName = "CommonService",//与前面接口一致
        targetNamespace = "http://www.webservice.demo.example.com",  //与前面接口一致
        endpointInterface = "com.example.demo.webservice.CommonService")  //接口地址
@Component
public class CommonServiceImpl implements CommonService {
    @Override
    public String HellWorld(String name) {
        return "hello," + name + "!";
    }

    @WebMethod(exclude = true)//该方法不会发布，默认值是false 注意cxf高版本中exclude = true不能写在接口方法上，必须写在实现方法上，否则报错
    @Override
    public String HellWorld2(String name, int age) {
        return "hello," + name + "!age="+age;
    }
}
