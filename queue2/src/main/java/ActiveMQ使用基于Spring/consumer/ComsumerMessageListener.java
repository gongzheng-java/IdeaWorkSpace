package ActiveMQ使用基于Spring.consumer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by gongzheng on 2019-09-26.
 */
public class ComsumerMessageListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage) message;
        try {
            System.out.println("接收到的消息是："+ textMessage.getText());
        }catch (Exception e){
             e.printStackTrace();
        }

    }
}
