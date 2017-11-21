package per.itachi.test.jms.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class UserOrderConsumer implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(UserOrderConsumer.class);

	@Override
	public void onMessage(Message msg) {
		logger.debug("A new user order {} has been received.", msg);
	}

}
