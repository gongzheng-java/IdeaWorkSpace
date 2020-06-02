package ActiveMQ使用基于Spring.producer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 生产者测试类
 * Created by gongzheng on 2019-09-26.
 */
public class ProducerTest {
    public static void main(String[] args) {
        /*ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("producer.xml");
        ProduceService bean = classPathXmlApplicationContext.getBean(ProduceService.class);
        //进行发送消息
        for (int i = 1; i <=100 ; i++) {
            bean.sendMessage("test:"+i);
        }
        //当消息发送完后，关闭容器
        classPathXmlApplicationContext.close();*/

        ApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");
        ProduceService bean = (ProduceService) context.getBean("produceService");
        //进行发送消息
        for (int i = 1; i <= 100; i++) {
            bean.sendMessage("test:" + i);
        }

    }

}
