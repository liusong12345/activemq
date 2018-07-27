package com.dyd.business.service.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.dyd.business.service.IProducer;

@Service
public class Producer implements IProducer{

	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	public void sendMessage(Destination destination, final String message){
		log.info("producer send {}",message);
		jmsTemplate.convertAndSend(destination, message);
	}
	
	public void sendMessage(Destination destination, final String message,final String jMSXGroupID){
		log.info("producer send message group {}",message);
		try {
			jmsTemplate.getJmsTemplate().send(destination, new MessageCreator() {
	            @Override
	            public Message createMessage(Session session) throws JMSException {
	                TextMessage textMessage = session.createTextMessage(message);
	                textMessage.setStringProperty("JMSXGroupID", jMSXGroupID);
	                return textMessage;
	        	}
	        });
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
