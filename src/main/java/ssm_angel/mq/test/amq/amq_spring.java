package ssm_angel.mq.test.amq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class amq_spring {
	
	
	
	@Test
	public void testAmq2Spring() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-amq.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session arg0) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage tm = arg0.createTextMessage("spring-test=====================================================");
				return tm;
			}
		});
	}
	
	@Test
	public void testRecive(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-amq.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		TextMessage message = (TextMessage) jmsTemplate.receive();
		try {
			System.out.println(message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

