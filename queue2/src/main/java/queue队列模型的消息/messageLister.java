package queue队列模型的消息;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听
 * Created by gongzheng on 2019-09-25.
 */
public class messageLister implements MessageListener {


    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("获取消息：" + textMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
