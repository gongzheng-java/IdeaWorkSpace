package ActiveMQ使用基于Spring.producer;

/**
 * 生产者接口
 * Created by gongzheng on 2019-09-26.
 */
public interface ProduceService {
    void sendMessage(String msg);
}
