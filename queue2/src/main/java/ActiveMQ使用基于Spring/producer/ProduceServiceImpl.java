package ActiveMQ使用基于Spring.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * 生产者实现类
 * Created by gongzheng on 2019-09-26.
 */
public class ProduceServiceImpl implements ProduceService {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "queueDestination")
    private Destination destination;

    @Override
    public void sendMessage(final String msg) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(msg);
                return message;
            }
        });
        System.out.println("发送的消息是："+msg);
    }
}
