package com.example.demo.config;

import com.example.demo.webservice.CommonService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Created by gongzheng on 2020-05-10.
 */
@Configuration
public class WebConfig {

    @Autowired
    private Bus bus;

    @Autowired
   private CommonService service;

    /**
     * 这里相当于把Commonservice接口发布在了路径/services/CommonService下,wsdl文档路径为
       http://localhost:8080/services/CommonService?wsdl
     浏览器输入：http://localhost:8080/serviceshttp://localhost:8080/services查看接口
     * @return
     */
    @Bean
    public Endpoint endpoint() {

        EndpointImpl endpoint = new EndpointImpl(bus, service);
        endpoint.publish("/CommonService");
        return endpoint;
    }
}
