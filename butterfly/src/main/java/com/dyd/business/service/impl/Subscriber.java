package com.dyd.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.dyd.business.service.ISubscriber;

@Service
public class Subscriber implements ISubscriber{

	private static final Logger log = LoggerFactory.getLogger(Subscriber.class);
	
	@Override
	@JmsListener(destination = "mytest.topic?consumer.retroactive=true",containerFactory="jmsListenerContainerTopic")
	public void subscribeTopic(String text) {
		log.info("subscriber receive message {}",text);
	}

}
