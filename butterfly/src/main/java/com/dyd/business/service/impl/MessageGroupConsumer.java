package com.dyd.business.service.impl;

import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.dyd.business.service.IConsumer;

//@Service
public class MessageGroupConsumer implements IConsumer{

	private static final Logger log = LoggerFactory.getLogger(MessageGroupConsumer.class);
	
	//JMSXGroupID='red'  consumer1
	@JmsListener(destination = "mytest.queue?consumer.priority=10&customer.prefetchSize=1000",
			selector="JMSXGroupID='red'",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue1(String text,Session session){
		log.info("consumer1 receive message {}",text);
	}
	
	//JMSXGroupID='red'  consumer11
	@JmsListener(destination = "mytest.queue?consumer.priority=11&customer.prefetchSize=1000",
			selector="JMSXGroupID='red'",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue11(String text,Session session){
		log.info("consumer11 receive message {}",text);
	}
	
	//JMSXGroupID='pink'  consumer2
	@JmsListener(destination = "mytest.queue?consumer.priority=10&customer.prefetchSize=1000",
			selector="JMSXGroupID='pink'",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue2(String text,Session session){
		log.info("consumer2 receive message {}",text);
	}
	
}
