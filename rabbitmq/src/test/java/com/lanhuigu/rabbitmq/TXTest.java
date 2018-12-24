package com.lanhuigu.rabbitmq;

import com.lanhuigu.rabbitmq.tx.TXConsumer;
import com.lanhuigu.rabbitmq.tx.TXSender;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息确认机制测试
 * @author yihonglei
 * @date 2018/12/24 15:25
 */
public class TXTest {

    @Test
    public void send() throws IOException, TimeoutException {
        TXSender.send();
    }

    @Test
    public void consume() throws InterruptedException, TimeoutException, IOException {
        TXConsumer.consume();
    }

}
