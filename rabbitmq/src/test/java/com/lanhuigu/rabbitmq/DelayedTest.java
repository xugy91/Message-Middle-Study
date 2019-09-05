package com.lanhuigu.rabbitmq;

import com.lanhuigu.rabbitmq.delayed.RoutingConsumer1;
import com.lanhuigu.rabbitmq.delayed.RoutingSender;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Rounting（路由模式测试）
 *
 * @author yihonglei
 * @date 2018/12/21 18:56
 */
public class DelayedTest {
    @Test
    public void send() throws IOException, TimeoutException {
        // 对应RountingKey的能收到消息
        // RoutingSender.send("error"); // 两个队列均含error key，都能被路由到，两个消费者都能收到消息
        RoutingSender.send("error"); // 只有队列2含有info，队列2被路由到，队列1路由不到消息，即consumer2能收到消息，consumer1收不到消息
    }

    @Test
    public void consumer1() throws InterruptedException, TimeoutException, IOException {
        RoutingConsumer1.consume();
    }
}
