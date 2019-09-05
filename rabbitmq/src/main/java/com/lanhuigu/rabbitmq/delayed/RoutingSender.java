package com.lanhuigu.rabbitmq.delayed;

import com.lanhuigu.rabbitmq.utils.CommonConsant;
import com.lanhuigu.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Rounting(路由模式)
 *
 * @author yihonglei
 * @date 2018/12/20 22:51
 */
public class RoutingSender {

    /**
     * 路由队列的key
     *
     * @author yihonglei
     * @date 2018/12/21 18:55
     */
    public static void send(String routingKey) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建通道
        final Channel channel = connection.createChannel();

        // //创建DLX及死信队列
        channel.exchangeDeclare(CommonConsant.DLX_EXCHANGE, "direct");
        channel.queueDeclare(CommonConsant.DLX_QUEUE, true, false, false, null);
        channel.queueBind(CommonConsant.DLX_QUEUE, CommonConsant.DLX_EXCHANGE, routingKey);

        //创建测试超时的Exchange及Queue
        channel.exchangeDeclare(CommonConsant.DELAY_EXCHANGE, "direct");


        Map<String, Object> arguments = new HashMap();
        //过期时间10s
        arguments.put("x-message-ttl", 10000);
        //绑定DLX
        arguments.put("x-dead-letter-exchange", CommonConsant.DLX_EXCHANGE);
        //绑定发送到DLX的RoutingKey
        arguments.put("x-dead-letter-routing-key", routingKey);
        channel.queueDeclare(CommonConsant.DELAY_QUEUE, true, false, false, arguments);
        channel.queueBind(CommonConsant.DELAY_QUEUE, CommonConsant.DELAY_EXCHANGE, routingKey);

        channel.basicPublish(CommonConsant.DELAY_EXCHANGE, routingKey, null, "该消息在10s后发送".getBytes());

        // 资源关闭
        channel.close();
        connection.close();
    }

}
