package queue主题模型的消息;

import org.apache.activemq.ActiveMQConnectionFactory;
import queue队列模型的消息.messageLister;

import javax.jms.*;

/**
 * 主题消费者
 * Created by gongzheng on 2019-09-25.
 */
public class MessageTopicConsumer {
    //定义ActivMQ的连接地址
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    //定义发送消息的主题名称
    private static final String TOPIC_NAME = "MyTopicMessage";

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
        Destination destination = session.createTopic(TOPIC_NAME);
        //创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //创建消费者监听
        consumer.setMessageListener(new messageLister());

    }
}
