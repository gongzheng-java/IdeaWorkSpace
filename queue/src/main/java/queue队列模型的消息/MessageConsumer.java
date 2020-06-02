package queue队列模型的消息;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;


/**
 * 消费者
 * Created by gongzheng on 2019-09-25.
 */
public class MessageConsumer {
    //定义ActivMQ的连接地址
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    //定义发送消息的队列名称
    private static final String QUEUE_NAME = "MyMessage";

    /**
     * 可以先多次运行消费者，让其处于等待状态，然后再去运行生产者，再回头看看控制台效果
     *
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //打开连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标(地址)
        Destination destination = session.createQueue(QUEUE_NAME);
        //创建消费者
        javax.jms.MessageConsumer consumer = session.createConsumer(destination);
        //创建消费者监听
        consumer.setMessageListener(new messageLister());

    }

}
