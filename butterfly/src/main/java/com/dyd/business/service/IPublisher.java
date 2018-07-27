package com.dyd.business.service;

import javax.jms.Destination;

public interface IPublisher {

	public void publishMessage(Destination destination, final String message);
}
