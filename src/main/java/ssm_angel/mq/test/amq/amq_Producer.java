package ssm_angel.mq.test.amq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class amq_Producer {
	
	private static final String url = "tcp://192.168.3.188:61616";
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		Session session = null;
		try {
			//创建amq的连接工厂
			connectionFactory = new ActiveMQConnectionFactory(url);
			//创建连接
			connection = connectionFactory.createConnection();
			connection.start();
			//创建会话
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建队列(用于存放消息)
			Destination destination = session.createQueue("my-queue");
			//创建消息生产者
			MessageProducer messageProducer = session.createProducer(destination);
			//创建五个消息对象
			for(int i=0;i<5;i++) {
				TextMessage tm = session.createTextMessage("message:"+i);
/*				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("key:"+i, "value:"+i);
				mapMessage.setStringProperty("kp:"+i, "vp:"+i);*/
					//发送消息
				messageProducer.send(tm);
			}
			//提交消息
			session.commit();
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
