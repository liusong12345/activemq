package com.dyd.business.service;

import javax.jms.Session;


public interface IConsumer {

	public void receiveQueue1(String text,Session session);
	
	public void receiveQueue2(String text,Session session);
	
}
