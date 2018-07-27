package com.dyd.business.service;

import javax.jms.Destination;
import javax.jms.Message;

public interface IProducer {

	public void sendMessage(Destination destination,final String message);
	
	public void sendMessage(Destination destination,final String message,final String jMSXGroupID);
	
	
}
