package per.itachi.test.jms.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class BlackListcConsumer implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(BlackListcConsumer.class);

	@Override
	public void onMessage(Message msg) {
		logger.debug("The black item {} has been received.", msg);
	}

}
