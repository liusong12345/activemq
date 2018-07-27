package com.dyd.business.service.impl;

import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.dyd.business.service.IConsumer;
//并在每次Topic收到消息时，分发到具体的queue。
@Service
public class VirtualTopicConsumer implements IConsumer{

	private static final Logger log = LoggerFactory.getLogger(VirtualTopicConsumer.class);
	
	//JMSXGroupID='red'  consumer1
	@JmsListener(destination = "VirtualTopicConsumers.A.virtualTopic?consumer.priority=10&customer.prefetchSize=1000",
			selector="JMSXGroupID='red'",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue1(String text,Session session){
		log.info("VirtualTopic consumer1 receive message {}",text);
	}
	
	//JMSXGroupID='red'  consumer11
	@JmsListener(destination = "VirtualTopicConsumers.A.virtualTopic?consumer.priority=11&customer.prefetchSize=1000",
			selector="JMSXGroupID='red'",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue11(String text,Session session){
		log.info("VirtualTopic consumer11 receive message {}",text);
	}
	
	//JMSXGroupID='pink'  consumer2
	@JmsListener(destination = "VirtualTopicConsumers.A.virtualTopic?consumer.priority=10&customer.prefetchSize=1000",
			selector="JMSXGroupID='pink'",
			containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue2(String text,Session session){
		log.info("VirtualTopic consumer2 receive message {}",text);
	}
	
}
