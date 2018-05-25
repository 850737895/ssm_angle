package ssm_angel.mq.test.amq;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class amq_consumer {
	
	private static final String url = "tcp://192.168.3.188:61716";
	
	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		Session session = null;
		
		try {
			connectionFactory = new ActiveMQConnectionFactory(url);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("my-queue");
			//
			MessageConsumer mc = session.createConsumer(destination);
			Enumeration<String> names = connection.getMetaData().getJMSXPropertyNames();
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				System.out.println("属性名:"+name);
			}
			int i=0;
			while(i<5) {
				TextMessage  message = (TextMessage) mc.receive();
				//MapMessage message = (MapMessage) mc.receive();
				//确定收到消息
				session.commit();
				System.out.println("从消息队列中获取:消息信息:"+message.getText());
				i++;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(session!=null) {
				try {
					session.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
