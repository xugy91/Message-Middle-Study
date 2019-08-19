package com.lanhuigu.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 消费者
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setInstanceName("Consumer");

        /*
         * Rocket默认开启了VIP通道，VIP通道端口为10911-2=10909。
         * 而broker默认端口为10911，找不到10909服务，则报connect to <：10909> failed。
         * 设置为false，关闭VIP通道。
         */
        consumer.setVipChannelEnabled(false);

        /*
         * 订阅指定topic下tags分别等于TagA 或 TagB 或TagC
         */
        consumer.subscribe("TopicTest", "TagA || TagB || TagC");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());

                // 执行topic的消费逻辑
                MessageExt msg = msgs.get(0);
                if (msg.getTopic().equals("TopicTest")) {
                    if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                        // 执行TagA消费
                        System.out.println("TagA========" + new String(msg.getBody()));
                    } else if (msg.getTags() != null && msg.getTags().equals("TagB")) {
                        // 执行TagB消费
                        System.out.println("TagB========" + new String(msg.getBody()));
                    } else if (msg.getTags() != null && msg.getTags().equals("TagC")) {
                        // 执行TagC消费
                        System.out.println("TagC========" + new String(msg.getBody()));
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("ConsumerStarted");
    }
}