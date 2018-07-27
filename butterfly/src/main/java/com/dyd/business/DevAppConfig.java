package com.dyd.business;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

@Configuration()
@EnableJms
@Profile("dev")
public class DevAppConfig{
	
	@Bean("mytestQueue")
	public Queue queue() {
		return new ActiveMQQueue("mytest.queue");
	}
	
	@Bean("mytestTopic")
	public Topic topic() {
		return new ActiveMQTopic("mytest.topic");
	}
	
	@Bean("virtualTopic")
	public Topic virtualTopic() {
		return new ActiveMQTopic("virtualTopic");
	}
	
	@Bean("compositeQueue")
	public Queue compositeQueue() {
		return new ActiveMQQueue("mytest.queue,topic://mytest.topic");
	}
	
	
	@Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
	
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        //设置连接数
        bean.setConcurrency("1-10");//???
        //重连间隔时间
        bean.setRecoveryInterval(1000L);
        //消息确认机制
        //=bean.setSessionAcknowledgeMode(1);//自动确认
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
	    
    
    //activemq链接工厂
	@Bean
    public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
		//为每一个Destination配置一个Redelivery Policy
		RedeliveryPolicyMap map = factory.getRedeliveryPolicyMap();
		map.put(new ActiveMQTopic(">"), redeliveryPolicy());
		map.put(new ActiveMQQueue(">"), redeliveryPolicy());
		factory.setDispatchAsync(true);
		factory.setOptimizeAcknowledge(true);//批处理操作确认一系列消息 ack必须自动确认才起效
		factory.setOptimizeAcknowledgeTimeOut(300);
        return factory;
    }

	/*ActiveMQ在接收消息的Client有以下几种操作的时候，需要重新传递消息：
	a：Client用了transactions，且在session中调用了rollback()
	b：Client用了transactions，且在调用commit()之前关闭
	c：Client在CLIENT_ACKNOWLEDGE的传递模式下，在session中调用了recover()*/
	@Bean
	public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy  redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   
        redeliveryPolicy.setMaximumRedeliveries(6);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);//???
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);//???
        return redeliveryPolicy;
	}

	
	@Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory){
        return new JmsMessagingTemplate(connectionFactory);
    }
}
