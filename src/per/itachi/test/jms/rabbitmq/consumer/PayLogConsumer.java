package per.itachi.test.jms.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class PayLogConsumer implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(PayLogConsumer.class);

	@Override
	public void onMessage(Message msg) {
		logger.debug("A new payment {} comes in", msg);
	}

}
