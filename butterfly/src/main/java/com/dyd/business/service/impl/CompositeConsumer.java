package com.dyd.business.service.impl;

import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.dyd.business.service.IConsumer;

@Service
public class CompositeConsumer implements IConsumer{

	private static final Logger log = LoggerFactory.getLogger(CompositeConsumer.class);
	
	@JmsListener(destination = "mytest.queue",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue1(String text,Session session){
		log.info("consumer1 receive message {}",text);
	}
	
	@JmsListener(destination = "mytest.queue",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue2(String text,Session session){
		log.info("consumer2 receive message {}",text);
	}
	
}
