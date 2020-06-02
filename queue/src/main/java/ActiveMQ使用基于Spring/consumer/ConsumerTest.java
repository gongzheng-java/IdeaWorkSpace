package ActiveMQ使用基于Spring.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by gongzheng on 2019-09-26.
 */
public class ConsumerTest {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("consumer.xml");
    }
}
