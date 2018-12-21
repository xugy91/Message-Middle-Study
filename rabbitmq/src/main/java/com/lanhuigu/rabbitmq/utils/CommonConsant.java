package com.lanhuigu.rabbitmq.utils;

/**
 * 常量
 * @author yihonglei
 * @date 2018/12/18 11:11
 */
public class CommonConsant {
    // ===================== 简单队列 =====================
    // 简单消息队列名称
    public final static String SIMPLE_QUEUE_NAME = "test-simple-queue";

    // ===================== 工作队列 =====================
    // 工作队列消息队列名称
    public final static String WORK_QUEUE_NAME = "test-work-queue";

    // ===================== S/P发布订阅模式-fanout =====================
    // 交换机名称
    public final static String EXCHANGE_NAME_FANOUT = "test-exchange-fanout";

    // 邮件队列名称
    public final static String EXCHANGE_NAME_FANOUT_EMAIL = "test-queue-email";

    // 短信队列名称
    public final static String EXCHANGE_NAME_FANOUT_SMS = "test-queue-sms";

    // ===================== Rounting路由模式-fanout =====================
    // 交换机名称
    public final static String EXCHANGE_NAME_DIRECT = "test-exchange-direct";

    // 路由队列1
    public final static String DIRECT_QUEUE1_NAME = "test-queue-direct1";

    // 路由队列2
    public final static String DIRECT_QUEUE2_NAME = "test-queue-direct2";

    // RountingKey-error,info,warning
    public final static String ROUNTING_KKEY_ERROR = "error";
    public final static String ROUNTING_KKEY_INFO = "info";
    public final static String ROUNTING_KKEY_WARNING = "warning";
}
