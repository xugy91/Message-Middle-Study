# 一 项目简介
基于maven构建，对RabbitMQ基本特性简单代码体验。

## src目录

1、utils

Connection连接工具类。

2、simple

简单队列，Hello World生产者和消费者实现。

3、work

一个生产者，对应两个生产者，两个生产者平均消费消息个数。

4、workfair

一个生产者，对应两个生产者，消息快的多消费消息，能者多劳。

5、ps

publish/subscribe 发布/定阅模式。

6、routing

路由模式，基于key路由队列消费。

7、topic

主题模式，基于topic生产消费。

8、tx

消息发送事务控制。

## test目录
对应src测试代码。

# 二 技术博客

【RabbitMQ】Hello World入门实战 https://blog.csdn.net/yhl_jxy/article/details/85262096




