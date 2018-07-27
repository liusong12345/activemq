package com.dyd.api;

import java.util.concurrent.atomic.AtomicLong;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dyd.business.model.Greeting;
import com.dyd.business.service.impl.Producer;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	
    private final AtomicLong counter = new AtomicLong();
 
    
    @Autowired
	private Producer producer;
    
    @RequestMapping(value="/greeting",method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(value="/queue",method = RequestMethod.GET)
    public String queue(@RequestParam(value="name", defaultValue="World") String name) {
    	Destination destination = new ActiveMQQueue("mytest.queue");
		producer.sendMessage(destination, name);
        return "{'rec':'ok'}";
    }

    
}
