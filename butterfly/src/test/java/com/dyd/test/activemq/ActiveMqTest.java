package com.dyd.test.activemq;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyd.business.service.impl.Producer;
import com.dyd.business.service.impl.Publisher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMqTest {

	@Autowired
	private Producer producer;
	
	@Autowired
	private Publisher publisher;
	
	@Resource(name="mytestQueue")
	private Queue queue;
	
	@Resource(name="mytestTopic")
	private Topic topic;
	
	@Resource(name="virtualTopic")
	private Topic virtualTopic;
	
	@Resource(name="compositeQueue")
	private Queue compositeQueue;
	
	@Test//virtual topic
	public void testVirtualTopic() throws InterruptedException {
		for(int i=0; i<1; i++){
			producer.sendMessage(virtualTopic, "virtualTopic red topic is " + i,"red");
			producer.sendMessage(virtualTopic, "virtualTopic pink topic is " + i,"pink");
		}
	}
	
	//@Test//mirror queue
	public void testMirrorQueue() throws InterruptedException {
		for(int i=0; i<1; i++){
			producer.sendMessage(queue, "mirror queue is " + i);
		}
	}
	
	//@Test//message group
	public void testCompositeQueue() throws InterruptedException {
		for(int i=0; i<1; i++){
			producer.sendMessage(compositeQueue, "composite queue is " + i);
		}
	}
	
	//@Test//message group
	public void testMGQueue() throws InterruptedException {
		for(int i=0; i<1; i++){
			producer.sendMessage(queue, "red queue is " + i,"red");
			producer.sendMessage(queue, "pink queue is " + i,"pink");
		}
	}
	
	//@Test
	public void testQueue() throws InterruptedException {
		for(int i=0; i<10; i++){
			producer.sendMessage(queue, "queue is " + i);
		}
	}
	
	//@Test
	public void testTopic() throws InterruptedException {
		for(int i=0; i<1; i++){
			publisher.publishMessage(topic, "topic is" + i);
		}
	}

}
