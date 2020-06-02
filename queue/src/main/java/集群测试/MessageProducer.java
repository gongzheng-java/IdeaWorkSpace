package 集群测试;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 用于消息的创建类
 * Created by gongzheng on 2019-09-26.
 */
public class MessageProducer {

    //通过集群的方式进行消息服务器的管理（failover就是进行动态转移，当某个服务器宕机，
    // 那么就进行其他的服务器选择,randomize表示随机选择）
    private static final String ACTIVEMQ_URL="failover:(tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=true";
    //定义发送消息的队列名称
    private static final String QUEUE_NAME = "MyMessage";

    public static void main(String[] args) throws JMSException{
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
       Connection connection= activeMQConnectionFactory.createConnection();
        //打开连接
        connection.start();
        //创建会话
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建队列目标地址
      Destination destination= session.createQueue(QUEUE_NAME);
        //创建生产者
       javax.jms.MessageProducer producer= session.createProducer(destination);
        //发送消息
        for (int i = 1; i <=100; i++) {
            TextMessage message=session.createTextMessage("消息-->"+i);
            producer.send(message);
            System.out.println("发送："+message.getText());
        }
        //关闭连接
        connection.close();
    }
}
