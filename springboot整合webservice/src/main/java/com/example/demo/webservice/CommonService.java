package com.example.demo.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by gongzheng on 2020-05-10.
 */
@WebService(name = "CommonService",//暴露服务名
               targetNamespace = "http://www.webservice.demo.example.com")// 命名空间
public interface CommonService {

    @WebMethod //表示要发布的方法
    @WebResult(name = "String")
    public String HellWorld(@WebParam(name = "name") String name);

    //
    @WebMethod()//该方法不会发布，默认值是false
    @WebResult(name = "String")
    public String HellWorld2(@WebParam(name = "name") String name,@WebParam(name = "age")int age);
}
