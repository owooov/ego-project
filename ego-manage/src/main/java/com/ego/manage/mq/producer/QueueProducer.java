package com.ego.manage.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/29 16:13
 * @Description:
 */
@Component
public class QueueProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;
    public void sendTextMassage(String id){
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(id);
            }
        });
    }
}
