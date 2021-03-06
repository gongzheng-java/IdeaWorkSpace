package queue队列模型的消息;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者
 * 用于消息的创建类
 */
public class MessageProducer {

    //定义ActivMQ的连接地址
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    //定义发送消息的队列名称
    private static final String QUEUE_NAME = "MyMessage";

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //打开连接
        connection.start();
        /**
         * 创建会话
         * 参数1： true or false 表示是否支持事务。
         *        值为false时，可以选下面其中一个值；值为true时，第二个参数值忽略，acknowledgment mode被jms服务器设置为SESSION_TRANSACTED 。
         * 参数2：取值有:Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE，SESSION_TRANSACTED
         *       Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。 
         *       Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。 
         *       DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标(地址)
        Destination destination = session.createQueue(QUEUE_NAME);
        //创建一个生产者
        javax.jms.MessageProducer producer = session.createProducer(destination);
        //创建模拟100个消息
        for (int i = 1; i <= 100; i++) {
            TextMessage message = session.createTextMessage("发送message：" + i);
            //发送消息
            producer.send(message);
            //本地打印消息
            System.out.println("刚才发送的消息是："+message.getText());
        }
        //关闭连接
        connection.close();


    }

}
