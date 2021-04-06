package com.example.orderservice.Listener;

import com.example.orderservice.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class RabbitMQListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues ="DLX_bs_queue")
    public void Listener(Message message, Channel Channel) throws Exception {
        //Thread.sleep(1000);
        long de = message.getMessageProperties().getDeliveryTag();
        log.info("-----------》接受定时检查支付时间是否超时"+de);
        try {
            String json = new String(message.getBody());
            orderService.zhifuchaoshi(json);
            //确定签收
            log.info("-----------》接受定时检查支付时间是否超时成功"+de);
            Channel.basicAck(de,true);
        }catch (Exception e) {
            //e.printStackTrace();
            //3.重发
            log.info("-----------》接受定时检查支付时间是否超时失败"+de);
            Channel.basicNack(de,true,false);
        }
    }
}
