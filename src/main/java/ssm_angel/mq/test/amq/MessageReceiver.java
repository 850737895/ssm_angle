package ssm_angel.mq.test.amq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息接受 通过消息监听器 来接受消息
 * @author 85073
 *
 */
public class MessageReceiver implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		if(arg0 instanceof TextMessage) {
			try {
				System.out.println("接受消息:"+((TextMessage) arg0).getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
