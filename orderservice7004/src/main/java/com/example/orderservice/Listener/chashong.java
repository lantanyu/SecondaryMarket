package com.example.orderservice.Listener;

import com.example.commons.po.order;
import com.example.orderservice.Config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class chashong {
    @Autowired
    private ApplicationContext applicationContext;

    public void fashong(String json) {
        log.info("-----------》发送定时检查支付时间是否超时");
        //确认是否成功到交换机
        RabbitTemplate rabbitTemplate= (RabbitTemplate) applicationContext.getBean("rabbit");
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override            //1.配置信息 2.是否成功接收 3.失败原因
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("==已发送");
                if(b) {
                    log.info("==发送成功到交换机"+s);
                }else {
                    log.error("==发失败到交换机"+s);
                }
            }
        });
        //设置交换机没到队列返回
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override                   //1.消息对象 2.错误码 3 错误信息 4 交互机 5。路右键
            public void returnedMessage(Message message, int i, String s, String s1, String s2){
                log.error("==没送到队列");
                log.error("错误码:"+i);
                log.error("错误信息:"+s);
                log.error("交互机:"+s1);
                log.error("路右键:"+s2);
                //System.out.println(message);

            }
        });
        //消息拒绝，过期，长度，都会传到死信交换机
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"TTL.haha",json);
        log.info("发送的信息是"+json);
        log.info("-----------》发送定时检查支付时间是否超时结束");
    }

    public order shiyan() {
        throw new RuntimeException("shiyan");
    }
}
