package ssm_angel.mq.test.amq.msgfailover;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MsgProducer {
	
	private static final String url = "tcp://192.168.3.188:61616";
	private static ConnectionFactory connectionFactory = null;
	private static Connection connection = null;
	private static Session session = null;
	
	
	public static void main(String[] args) {
	
			try {
				//创建amq的连接工厂
				connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://192.168.3.188:61616,"
						+ "tcp://192.168.3.188:61716)?randomize=false");
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
				for(int i=0;i<30;i++) {
					TextMessage tm = session.createTextMessage("message:"+i);
					//发送消息
					messageProducer.send(tm);
				}
				//提交消息
				session.commit();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
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

