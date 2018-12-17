package com.lanhuigu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RabbitMQ生产-->消费测试
 * @author yihonglei
 * @date 2018/12/13 17:07
 */
public class RabbitMQTest {
    // 消息队列名称
    private final static String QUEUE_NAME = "hello-yhl";

    /**
     * 消息生产者
     * @author yihonglei
     * @date 2018/12/13 18:05
     */
    @Test
    public void send() throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建消息通道
        Channel channel = connection.createChannel();

        // 生成一个消息队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 消息发送
        for (int i = 0; i < 10; i++) {
            String message = "Hello World RabbitMQ count：" + i;
            // 发布消息，第一个参数表示路由（Exchange名称），未""则表示使用默认消息路由
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println(" Sent '" + message + "'");
        }

        // 关闭消息通道和连接
        channel.close();
        connection.close();
    }

    /**
     * 消息消费者
     * @author yihonglei
     * @date 2018/12/13 18:05
     */
    @Test
    public void consumer() throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建消息通道
        Channel channel = connection.createChannel();

        // 声明消息队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("[*] Waiting for message. To exist press CTRL+C");

        AtomicInteger count = new AtomicInteger();
        // 消费者用于获取消息信道绑定的消息队列中的信息
        Consumer consumer = new DefaultConsumer(channel) {
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

        channel.basicConsume(QUEUE_NAME, false, consumer);
        Thread.sleep(1000 * 60);
    }
}
