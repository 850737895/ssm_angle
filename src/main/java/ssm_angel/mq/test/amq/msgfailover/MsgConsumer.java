package ssm_angel.mq.test.amq.msgfailover;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MsgConsumer {
	
	private static final String url = "tcp://192.168.3.188:61716";
	private static ConnectionFactory connectionFactory = null;
	private static Connection connection = null;
	private static Session session = null;
	
	public static void main(String[] args) {
		
		try {
			connectionFactory = new ActiveMQConnectionFactory(url);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("my-queue");
			
			MessageConsumer mc = session.createConsumer(destination);
			for(int i=0;i<10;i++) {
				TextMessage  message = (TextMessage) mc.receive();
				Thread.sleep(1000);
				session.commit();
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
