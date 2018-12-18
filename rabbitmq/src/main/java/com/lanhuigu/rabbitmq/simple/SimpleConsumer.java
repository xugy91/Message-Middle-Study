package com.lanhuigu.rabbitmq.simple;

import com.lanhuigu.rabbitmq.utils.ConnectionUtil;
import com.lanhuigu.rabbitmq.utils.QueueConsant;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单队列--消费者
 * @author yihonglei
 * @date 2018/12/18 11:13
 */
public class SimpleConsumer {

    /**
     * 消息消费者
     * @author yihonglei
     * @date 2018/12/13 18:05
     */
    public static void consumer() throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建消息通道
        Channel channel = connection.createChannel();

        // 声明消息队列
        channel.queueDeclare(QueueConsant.SIMPLE_QUEUE_NAME, true, false, false, null);
        System.out.println("[*] Waiting for message. To exist press CTRL+C");

        AtomicInteger count = new AtomicInteger();
        // 消费者用于获取消息信道绑定的消息队列中的信息
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                try {
                    System.out.println(" Received " + message);
                } finally {
                    System.out.println(" Done ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(QueueConsant.SIMPLE_QUEUE_NAME, false, consumer);
        Thread.sleep(1000 * 60);
    }

}
