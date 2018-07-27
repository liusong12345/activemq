package com.dyd.business.service.impl;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.dyd.business.service.IPublisher;

@Service
public class Publisher implements IPublisher{

	private static final Logger log = LoggerFactory.getLogger(Publisher.class);
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	@Override
	public void publishMessage(Destination destination, String message) {
		log.info("publisher send {}",message);
		jmsTemplate.convertAndSend(destination, message);
	}

	
}
