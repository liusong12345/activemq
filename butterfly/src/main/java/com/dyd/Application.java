package com.dyd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.dyd.business.service.impl.Producer;

@SpringBootApplication
public class Application implements CommandLineRunner{
	 
	@Autowired
	private Producer producer;
	
	private static ApplicationContext applicationContext;
	
	//对于一个final变量，如果是基本数据类型的变量，则其数值一旦在初始化之后便不能更改；如果是引用类型的变量，则在对其初始化之后便不能再让其指向另一个对象。
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		setApplicationContext(SpringApplication.run(Application.class, args));
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();  
		for (String profile : activeProfiles) {  
			log.info("Spring Boot profile = {} ",profile);
		}  
	}
	
	public static void setApplicationContext(ApplicationContext context) {
	    applicationContext = context;
	}
	
	 //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
 
    }
 
    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
 
    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

	@Override
	public void run(String... args) throws Exception {
		/*Destination destination = new ActiveMQQueue("mytest.queue");
		for(int i=0; i<1; i++){
			producer.sendMessage(destination, "queue is " + i);
		}*/
		
	}

    
}
